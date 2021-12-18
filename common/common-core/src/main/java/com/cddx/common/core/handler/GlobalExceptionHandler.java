package com.cddx.common.core.handler;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.web.response.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 全局异常处理器
 *
 * @author 范劲松
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 异常区域突出
     */
    private static final Integer TEN = 10;

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestUri, e.getMethod());
        throwing(e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult handleCustomException(CustomException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUri, e);
        throwing(e);
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestUri, e);
        throwing(e);
        return AjaxResult.error(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestUri, e);
        throwing(e);
        return AjaxResult.error(ResultEnum.UNKNOWN_ERROR);
    }

    private void throwing(Exception ex) {
        log.info("[信息]异常：" + ex.getMessage());
        log.info("[信息]异常：" + Arrays.toString(ex.getStackTrace()));
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < ex.toString().length() + TEN; k++) {
            sb.append("*");
        }
        log.info(sb.toString());
        log.info("*");
        log.info("*     日志定位：error_" + ex.hashCode());
        log.info("*     异常信息：" + ex);
        log.info("*     相关位置：");
        for (int i = 0; i < ex.getStackTrace().length; i++) {
            StackTraceElement stackTraceElement = ex.getStackTrace()[i];
            if (stackTraceElement.getClassName().startsWith("com.cddx")) {
                log.info("*" + stackTraceElement);
            }
        }
    }

}
