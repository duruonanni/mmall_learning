package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.service.IProductService;
import com.mmall.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<ProductDetailVo> detail(Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping(value = "/{productId}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> detailRESTful(@PathVariable Integer productId){
        return iProductService.getProductDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list
            (@RequestParam(value = "keyword",required = false)String keyword,
             @RequestParam(value = "categoryId",required = false) Integer categoryId,
             @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
             @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
             @RequestParam(value = "orderBy",defaultValue = "desc")String orderBy) {
        return iProductService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);
    }

    //http://tomcat.imooc.com/product/keyword/手机/1/10/price_asc
    @RequestMapping(value="/keyword/{keyword}/{pageNum}/pageSize}/{orderBy}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful
            (@PathVariable(value = "keyword")String keyword,
             @PathVariable(value = "pageNum")Integer pageNum,
             @PathVariable(value = "pageSize")Integer pageSize,
             @PathVariable(value = "orderBy")String orderBy) {
        if(pageNum == null) {
            pageNum = 1;
        }
        if(pageSize == null) {
            pageSize = 10;
        }
        if(StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(keyword,null,pageNum,pageSize,orderBy);
    }

    //http://tomcat.imooc.com/product/categoryId/100012/1/10/price_asc
    @RequestMapping(value="/categoryId/{categoryId}/{pageNum}/pageSize}/{orderBy}",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo> listRESTful
            (@PathVariable(value = "categoryId")Integer categoryId,
             @PathVariable(value = "pageNum")Integer pageNum,
             @PathVariable(value = "pageSize")Integer pageSize,
             @PathVariable(value = "orderBy")String orderBy) {
        if(pageNum == null) {
            pageNum = 1;
        }
        if(pageSize == null) {
            pageSize = 10;
        }
        if(StringUtils.isBlank(orderBy)) {
            orderBy = "price_asc";
        }
        return iProductService.getProductByKeywordCategory(null,categoryId,pageNum,pageSize,orderBy);
    }

}
