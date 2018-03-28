package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CooKieUtil {

    private final static Logger logger = LoggerFactory.getLogger(CooKieUtil.class);

    private final static String COOKIE_DOMAIN = ".imooc.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("read cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)) {
                    logger.info("return cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToKen(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME,token);
        cookie.setDomain(COOKIE_DOMAIN);
        // 代表设置cookie在根目录下
        cookie.setPath("/");

        // 设置cookie的有效期,单位是秒,设置为-1为永久时间,这里的有效期设置为一年
        // 设置此属性后,cookie会写入硬盘,默认情况下是只写入内存中的
        cookie.setMaxAge(60 * 60 * 24 * 365);
        logger.info("write cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
        response.addCookie(cookie);
    }

    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(StringUtils.equals(cookie.getName(),COOKIE_NAME)) {
                    cookie.setDomain(COOKIE_DOMAIN);
                    cookie.setPath("/");
                    // 将Cookie的有效时间置为0,可以删除此Cookie
                    cookie.setMaxAge(0);
                    logger.info("del cookieName:{},cookieValue:{}",cookie.getName(),cookie.getValue());
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

}
