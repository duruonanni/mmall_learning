package com.mmall.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {

        logger.error("{} Exception",httpServletRequest.getRequestURI(),e);
        // 使用Jackson2.x的时候,在ModelAndView中使用MappingJackson2View,课程中使用1.9
        // 所以使用过时的MappingJacksonJsonView()
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        // 目的是构建modelAndView的json并进行返回
        modelAndView.addObject("status",ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg","接口异常,详情查看服务端日志");
        modelAndView.addObject("data",e.toString());

        return modelAndView;
    }
}
