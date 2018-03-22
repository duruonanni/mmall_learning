package com.mmall.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 作用是读取配置文件
 */
public class PropertiesUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private static Properties props;

    // 静态变量使用静态代码块进行初始化,效率更高
    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            logger.error("配置文件读取异常",e);
        }
    }

    public static String getProperty(String key){
        // 使用trim,去除配置文件中可能存在的多余空格
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    /**
     * 重载此方法,当传入defaultValue参数时,使用该参数的值作为配置的值
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getProperty(String key,String defaultValue){
        // 使用trim,去除配置文件中可能存在的多余空格
        String value = props.getProperty(key.trim());
        if(StringUtils.isBlank(value)) {
            return defaultValue.trim();
        }
        return value.trim();
    }
}
