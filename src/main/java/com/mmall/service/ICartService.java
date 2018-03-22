package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;
import org.springframework.stereotype.Service;

public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer ProductId, Integer count);

    ServerResponse<CartVo> update(Integer userId,Integer ProductId,Integer count);

    ServerResponse<CartVo> deleteProduct(String productIds,Integer userId);

    ServerResponse<CartVo>  list(Integer userId);

    ServerResponse<CartVo> selectOrUnselect (Integer userId,Integer checked,Integer productId);

    ServerResponse<Integer> getProductCount(Integer userId);

}
