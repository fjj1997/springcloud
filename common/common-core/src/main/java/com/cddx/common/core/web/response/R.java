package com.cddx.common.core.web.response;

import com.cddx.common.core.enums.ResultEnum;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @author 范劲松
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 成功
     */
    public static final ResultEnum SUCCESS = ResultEnum.SUCCESS_CODE;

    /**
     * 失败
     */
    public static final ResultEnum FAIL = ResultEnum.SERVICE_ERROR;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS.getCode(), SUCCESS.getMsg());
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS.getCode(), null);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS.getCode(), msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL.getCode(), FAIL.getMsg());
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL.getCode(), msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL.getCode(), null);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL.getCode(), msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
