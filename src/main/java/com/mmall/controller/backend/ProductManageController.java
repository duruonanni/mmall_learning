package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest httpServletRequest, Product product) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iProductService.saveOrUpdateProduct(product);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iProductService.saveOrUpdateProduct(product);
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSellStatus(HttpServletRequest httpServletRequest, Integer productId,Integer status) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iProductService.setSaleStatus(productId,status);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iProductService.setSaleStatus(productId,status);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest httpServletRequest, Integer productId) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iProductService.manageProduceDetail(productId);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iProductService.manageProduceDetail(productId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iProductService.getProductList(pageNum,pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iProductService.getProductList(pageNum,pageSize);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest httpServletRequest, String productName,Integer productId,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,@RequestParam(value = "pageSize",defaultValue = "10")int pageSize) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {

        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请以管理员身份登录");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file,path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            Map fileMap = Maps.newHashMap();
//            fileMap.put("uri",targetFileName);
//            fileMap.put("url",url);
//
//            return ServerResponse.createBySuccess(fileMap);
//        } else {
//            return ServerResponse.createByErrorMessage("非管理员登录,无权限操作");
//        }
        // v2.0 使用拦截器验证登录与权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        Map fileMap = Maps.newHashMap();
        fileMap.put("uri",targetFileName);
        fileMap.put("url",url);

        return ServerResponse.createBySuccess(fileMap);
    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {

        Map resultMap = Maps.newHashMap();

        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            resultMap.put("success",false);
//            resultMap.put("msg","用户未登录,请以管理员身份登录");
//            return resultMap;
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            resultMap.put("success",false);
//            resultMap.put("msg","用户未登录,请以管理员身份登录");
//            return resultMap;
//        }
        // 富文本使用simditor框架,所以需要按照其要求的json格式返回数据,才能被正确读取
        // 所以,我们采用Map对数据格式进行重新构建,而不是使用之前的ServerResponse
        /*
           {
                "success": true/false,
                "msg": "error message", # optional
                "file_path": "[real file path]"
           }
         */
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileName = iFileService.upload(file,path);
//            if(StringUtils.isBlank(targetFileName)) {
//                resultMap.put("success",false);
//                resultMap.put("msg","上传失败");
//                return resultMap;
//            }
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
//
//            resultMap.put("success",true);
//            resultMap.put("msg","上传成功");
//            resultMap.put("file_path",url);
//            // simditor要求返回的信息添加responseHeader
//            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
//            return resultMap;
//        } else {
//            resultMap.put("success",false);
//            resultMap.put("msg","非管理员登录,无权限操作");
//            return resultMap;
//        }
        // v2.0 使用拦截器验证登录与权限
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = iFileService.upload(file,path);
        if(StringUtils.isBlank(targetFileName)) {
            resultMap.put("success",false);
            resultMap.put("msg","上传失败");
            return resultMap;
        }
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;

        resultMap.put("success",true);
        resultMap.put("msg","上传成功");
        resultMap.put("file_path",url);
        // simditor要求返回的信息添加responseHeader
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
