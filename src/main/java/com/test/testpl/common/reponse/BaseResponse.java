package com.test.testpl.common.reponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BaseResponse {
    public BaseResponse() {
        this.success = true;
    }

    private BaseResponse(Object data) {
        this.data = data;
        this.success = true;
    }

    private BaseResponse(boolean success, String msg) {
        this.success = success;
        this.message = msg;
    }

    private BaseResponse(boolean success, String msg, Object data) {
        this.success = success;
        this.message = msg;
        this.data = data;
    }

    // 请求是否成功
    private boolean success = false;
    // 描述信息
    private String message;
    // 返回数据
    private Object data = "";

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static BaseResponse success(Object obj) {
        return new BaseResponse(obj);
    }

    public static BaseResponse error(String message) {
        return new BaseResponse(false, message, null);
    }

    public static BaseResponse error(String message, Object object) {
        return new BaseResponse(false, message, object);
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
