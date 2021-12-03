package com.cddx.common.core.exception;

import com.cddx.common.core.enums.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义异常
 *
 * @author 范劲松
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    public CustomException(String message) {
        this.message = message;
    }

    public CustomException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    public CustomException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }

    /**
     * 业务异常
     *
     * @param responseEnum 异常状态编码
     */
    public CustomException(ResponseEnum responseEnum) {
        super(responseEnum.getMsg());
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMsg();
    }

}
