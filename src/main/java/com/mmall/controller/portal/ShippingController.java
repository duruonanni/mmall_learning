package com.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Shipping;
import com.mmall.pojo.User;
import com.mmall.service.IShippingService;
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
@RequestMapping("/shipping/")
public class ShippingController {

    @Autowired
    private IShippingService iShippingService;

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest httpServletRequest, Shipping shipping) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        return iShippingService.add(user.getId(),shipping);
    }

    @RequestMapping("del.do")
    @ResponseBody
    public ServerResponse add(HttpServletRequest httpServletRequest,Integer shippingId) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        return iShippingService.del(user.getId(),shippingId);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpServletRequest httpServletRequest, Shipping shipping) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        return iShippingService.update(user.getId(),shipping);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse<Shipping> select(HttpServletRequest httpServletRequest,Integer shippingId) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        return iShippingService.select(user.getId(),shippingId);
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                         HttpServletRequest httpServletRequest) {
        //        User user = (User) session.getAttribute(Const.CURRENT_USER); //v1.0
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)) {
            return ServerResponse.createByErrorMessage("用户未登录,无法获取当前用户的信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2Obj(userJsonStr,User.class);
        if(user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
        }
        return iShippingService.list(user.getId(),pageNum,pageSize);
    }

}
