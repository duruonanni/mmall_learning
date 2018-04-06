package com.mmall.controller.common.intercepter;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CooKieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class AuthorityInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    // controller调用前调用该方法,返回值决定是(true)否进入对应的controller
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        logger.info("preHandle");
        // 对handler进行强转,以获取Controller中的方法信息
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // 解析handlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className = handlerMethod.getBean().getClass().getSimpleName();

        // 解析方法参数,并打印日志
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator it = paramMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();

            String mapKey = (String)entry.getKey();
            // httpServletRequest.getParameterMap()返回的value值是String[]
            // 要获取这个值,需要进行判定与强转
            String mapValue = StringUtils.EMPTY;
            Object obj = entry.getValue();
            if(obj instanceof String[]) {
                String[] strs = (String[])obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey + "=" + mapValue);
        }

        if(StringUtils.equals(className,"UserManageController") && StringUtils.equals(methodName,"login")) {
            logger.info("拦截器拦截到请求,className:{},methodName:{}",className,methodName );
            // 注意登录日志中,不要打印用户敏感的密码等信息
            return true;
        }

        // 打印参数信息
        logger.info("拦截器拦截到请求,className:{},methodName:{},param:{}",className,methodName,requestParamBuffer);

        // 处理用户登录判断
        User user = null;
        String loginToken = CooKieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isNotEmpty(loginToken)) {
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.string2Obj(userJsonStr,User.class);
        }

        if(user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)) {
            // 执行reset方法的原因,避免调用getWriter()方法时,出现异常 : getWriter() has already been called for this response.
            httpServletResponse.reset();
            // 将返回信息设置为Json,使用v1.0中的ServerResponse包装返回结果
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json;charset=UTF-8");

            PrintWriter out = httpServletResponse.getWriter();

            // ProductManageController的richtextImgUpload方法,需要特殊的Map返回值,在此处进行定制
            if(user == null) {
                if(StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,"richtextImgUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success",false);
                    resultMap.put("msg","用户未登录,请以管理员身份登录");
                    out.print(JsonUtil.obj2String(resultMap));
                } else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户未登录")));
                }
            }else{
                if(StringUtils.equals(className,"ProductManageController") && StringUtils.equals(methodName,"richtextImgUpload")) {
                    Map resultMap = Maps.newHashMap();
                    resultMap.put("success", false);
                    resultMap.put("msg", "用户非管理员,无权限操作");
                    out.print(JsonUtil.obj2String(resultMap));
                }else{
                    out.print(JsonUtil.obj2String(ServerResponse.createByErrorMessage("拦截器拦截,用户非管理员")));
                }
            }
            out.flush();
            out.close();

            return false;
        }

        return true;
    }

    // controller调用后调用该方法
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("postHandle");
    }

    // 在返回ModelAndView前调用一次
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        logger.info("afterCompletion");
    }
}
