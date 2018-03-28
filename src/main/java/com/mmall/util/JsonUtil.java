package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

// 这是一个全面的JsonUtil,用于Obj对象和String的相互转换(转换成对象的Json格式数据)
public class JsonUtil {

    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 将对象全部字段列入
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        // 取消默认转换timestamps的形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        // 忽略空Bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);
        // 将所有日期格式统一
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));
        // 忽略未知属性错误(json中存在,但Java对象中不存在的属性)
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> String obj2String(T obj) {
        if(obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.warn("Parse object to String error",e);
            return null;
        }
    }

    // 解析成排版优化后的String
    public static <T> String obj2StringPretty(T obj) {
        if(obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            logger.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz) {
        if(StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            logger.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if(StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str : objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            logger.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?> ... elementClasses) {

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);

        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            logger.warn("Parse String to Object error",e);
            return null;
        }
    }

    // 通过测试可知,在传入的Obj是泛型集合时,前面封装的方法就无法起作用了
    // 因为string2Obj传入的class类型无法简单确定
    // 需要封装新的反序列方法
    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setEmail("duruo@happymmall.com");

        User u2 = new User();
        u1.setId(2);
        u1.setEmail("duruo2@happymmall.com");

        String userJson = JsonUtil.obj2String(u1);
        String userJsonPretty = JsonUtil.obj2StringPretty(u1);

        logger.info("userJson:{}",userJson);
        logger.info("userJsonPretty:{}",userJsonPretty);

        User jsonUser = JsonUtil.string2Obj(userJson,User.class);

        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String userListStr = JsonUtil.obj2StringPretty(userList);

        logger.info("===============");
        logger.info(userListStr);



        System.out.println("end");
    }


}
