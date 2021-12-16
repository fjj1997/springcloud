package com.cddx.common.core.exception;

/**
 * 验证码错误异常类
 *
 * @author 范劲松
 */
public class CaptchaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg) {
        super(msg);
    }
}
