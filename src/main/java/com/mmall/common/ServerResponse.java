package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// 保证序列化json时,如果时null对象,key也不会返回
public class ServerResponse<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    /*
        由于泛型的特征,在调用ServerResponse(int status, T data)方法时
        如果参数data是String类型时,会因为参数明确的ServerResponse(int status, String msg)方法优先级高于泛型方法
        所以会调用到msg为参数的构造方法(参数就无法传递到data中),这是直接调用此带泛型构造方法需要注意的点
        这里由于不直接调用类的构造方法,所以巧妙的规避了这个问题
     */
    private ServerResponse(int status) {
        this.status = status;
    }
    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }
    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    @JsonIgnore // 使结果中的json不再序列化
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T> ServerResponse<T> createBySuccess() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
    }
    // 传入参数明确限定是泛型T的,所以调用此方法,即使传入参数是String,也能正确将参数传递给data,而不是msg
    public static <T> ServerResponse<T> createBySuccess(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),data);
    }
    public static <T> ServerResponse<T> createBySuccessMessage(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg);
    }
    public static <T> ServerResponse<T> createBySuccess(String msg,T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),msg,data);

    }
    public static <T> ServerResponse<T> createByError() {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponse<T> createByErrorMessage(String msg) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(),msg);
    }
    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage) {
        return new ServerResponse<>(errorCode,errorMessage);
    }

}
