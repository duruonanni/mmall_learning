package com.mmall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.CartMapper;
import com.mmall.dao.ProductMapper;
import com.mmall.pojo.Cart;
import com.mmall.pojo.Product;
import com.mmall.service.ICartService;
import com.mmall.util.BigDecimalUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.vo.CartProductVo;
import com.mmall.vo.CartVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    public ServerResponse<CartVo> add(Integer userId,Integer ProductId,Integer count) {
        if(ProductId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        // 先查询购物车记录中是否存在该记录
        Cart cart = cartMapper.selectCartByUSerIdProductId(userId,ProductId);
        if(cart == null) {
            // 购物车数据库中没有相关记录,说明产品未添加进购物车中过,就需要进行添加
            Cart cartItem = new Cart();
            cartItem.setUserId(userId);
            cartItem.setProductId(ProductId);
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else {
            // 购物车中存在相关记录,说明需要更新之前的记录(主要是修改商品数量)
            count += cart.getQuantity();
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        return list(userId);
    }

    public ServerResponse<CartVo> update(Integer userId,Integer ProductId,Integer count) {
        if(ProductId == null || count == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectCartByUSerIdProductId(userId,ProductId);
        if(cart != null) {
            // 更新商品数量采用的覆盖,而不是在原基础上相加
            cart.setQuantity(count);
        }
        cartMapper.updateByPrimaryKeySelective(cart);
        return list(userId);
    }

    public ServerResponse<CartVo> deleteProduct(String productIds,Integer userId) {
        List<String> productList = Splitter.on(",").splitToList(productIds);
        if(CollectionUtils.isEmpty(productList)) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductId(userId,productList);

        return list(userId);
    }

    public ServerResponse<CartVo>  list(Integer userId) {
        CartVo cartVo = this.getCartByLimit(userId);
        return ServerResponse.createBySuccess(cartVo);
    }

    public ServerResponse<CartVo> selectOrUnselect (Integer userId,Integer checked,Integer productId) {
        cartMapper.checkedOrUncheckedProduct(userId,checked,productId);
        return list(userId);
    }

    public ServerResponse<Integer> getProductCount(Integer userId) {
        if(userId == null) {
            return ServerResponse.createBySuccess(0);
        }
        return ServerResponse.createBySuccess(cartMapper.selectCartProductCount(userId));
    }


    private CartVo getCartByLimit(Integer userId) {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(CollectionUtils.isNotEmpty(cartList)) {
            for (Cart CartItem : cartList) {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(CartItem.getId());
                cartProductVo.setUserId(CartItem.getUserId());
                cartProductVo.setProductId(CartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(CartItem.getProductId());
                if(product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    // 判断库存
                    int buyLimitCount = 0;
                    if(product.getStock() >= CartItem.getQuantity()) {
                        // 库存充足时,buyLimitCount设置为当前购物车需求值
                        buyLimitCount = CartItem.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    } else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        // 库存货物比购物车想要的多,则更新购物车中的stock为仓库的最大值
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(CartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                }
                // 计算总价
                BigDecimal totalPrice =
                        BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity());
                cartProductVo.setProductTotalPrice(totalPrice);
                cartProductVo.setProductChecked(CartItem.getChecked());
                // 判定将勾选的商品总价添加到购物车总价中去
                if(CartItem.getChecked() == Const.Cart.CHECKED) {
                    cartTotalPrice =
                            BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
            cartVo.setCartTotalPrice(cartTotalPrice);
            cartVo.setCartProductVoList(cartProductVoList);
            cartVo.setAllChecked(this.getAllCheckedStatus(userId));
            cartVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        }
        return cartVo;
    }

    private boolean getAllCheckedStatus(Integer userId) {
        if(userId == null) {
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }

}
