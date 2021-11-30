package cn.jj.amqp.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @author by wanghui03
 * @Classname CommonResponse
 * @Description TODO
 * @Date 2021/11/30 10:41
 */
public class CommonResponse<T> implements Serializable {

    private static final long serialVersionUID = -8879455279107918136L;

    private String message;
    private T data;
    private int code;

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return code == 200;
    }

    public CommonResponse() {
    }

    public static <T> CommonResponse<T> error(String message){
        return new CommonResponse<>(null,999,message);
    }

    public static <T> CommonResponse<T> success(T data) {
        return new CommonResponse<>(data,200,null);
    }

    public static <T> CommonResponse<T> popup(String message, T data) {
        return new CommonResponse<>(data,987,message);
    }

    public CommonResponse(T data, int code, String msg) {
        this.data = data;
        this.code = code;
        this.message = msg;
    }

}

