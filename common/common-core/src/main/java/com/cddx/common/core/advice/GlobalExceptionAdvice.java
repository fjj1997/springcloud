package com.cddx.common.core.advice;

import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.core.enums.ResultEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
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

    @ExceptionHandler(value = Exception.class)
    public AjaxResult defaultError(Exception ex, HttpServletResponse response) throws Exception {
        this.throwing(ex);
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            response.setStatus(400);
            return AjaxResult.error(ResultEnum.SERVICE_ERROR);
        } else if (ex instanceof HttpMessageNotReadableException) {
            response.setStatus(400);
            return AjaxResult.error(ResultEnum.SERVICE_ERROR);
        } /*else if (ex instanceof NoHandlerFoundException) {
            response.setStatus(404);
            return AjaxResult.error(ResultEnum.SERVICE_ERROR);
        } */else {
            response.setStatus(500);
            return AjaxResult.error(ResultEnum.SERVICE_ERROR);
        }
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
