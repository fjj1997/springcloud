package com.cddx.common.core.advice;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.web.response.AjaxResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 全局异常处理器
 *
 * @author 范劲松
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 异常区域突出
     */
    private static final Integer TEN = 10;

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(CustomException.class)
    public AjaxResult handleCustomExceptionn(CustomException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(ResultEnum.UNKNOWN_ERROR);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
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
