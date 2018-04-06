package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CooKieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisPoolUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0")int parentId) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录后重试");
//        }
//        // 校验user是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.addCategory(categoryName, parentId);
//        }else {
//            return ServerResponse.createByErrorMessage("用户无权限操作,需要管理员权限");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iCategoryService.addCategory(categoryName, parentId);
    }

    // todo 缺少删除分类 : 删除分类涉及删除该分类的子分类,以及该分类与子分类下的商品信息

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录后重试");
//        }
//        // 校验user是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            return iCategoryService.updateCategoryName(categoryId,categoryName);
//        }else {
//            return ServerResponse.createByErrorMessage("用户无权限操作,需要管理员权限");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iCategoryService.updateCategoryName(categoryId,categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录后重试");
//        }
//        // 校验user是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//           // 查询子节点的category信息,并且不递归,保持平级
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        }else {
//            return ServerResponse.createByErrorMessage("用户无权限操作,需要管理员权限");
//        }
        // v2.0 使用拦截器验证登录与权限
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0")Integer categoryId){
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
//        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)) {
//            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2Obj(userJsonStr,User.class);
//        if(user == null) {
//            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录后重试");
//        }
//        // 校验user是否为管理员
//        if(iUserService.checkAdminRole(user).isSuccess()) {
//            // 查询子节点的category信息,和所有递归子节点的id
//            return  iCategoryService.selectCategoryAndChildrenById(categoryId);
//        }else {
//            return ServerResponse.createByErrorMessage("用户无权限操作,需要管理员权限");
//        }
        // v2.0 使用拦截器验证登录与权限
        return  iCategoryService.selectCategoryAndChildrenById(categoryId);
    }

}
