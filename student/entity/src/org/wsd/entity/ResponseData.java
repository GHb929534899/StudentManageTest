package org.wsd.entity;

public class ResponseData<T> {
    Integer code;
    String message;
    T Data;

    /*public Response() {
    }*/

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        Data = data;
    }

    public Integer getCode() { return code; }

    public void setCode(Integer code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public T getData() { return Data; }

    public void setData(T data) { Data = data; }
}
