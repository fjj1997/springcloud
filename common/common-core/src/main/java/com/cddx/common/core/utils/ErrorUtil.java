package com.cddx.common.core.utils;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;

import java.util.Objects;

/**
 * 异常处理辅助类
 * @author spirit
 * @version 1.0
 * @date 2019-12-05 16:54
 */
public class ErrorUtil {

    /**
     * 抛出业务异常
     * @param resultEnum 异常编码
     */
    public static void message(ResultEnum resultEnum) {
        throw new CustomException(resultEnum);
    }

    /**
     * 抛出业务异常
     * @param message 异常信息
     */
    public static void message(String message) {
        throw new CustomException(message);
    }

    /**
     * 当条件判断为假时，抛出异常
     * @param judgment 需要判断的条件
     * @param resultEnum 异常信息描述
     */
    public static void isFalse(boolean judgment, ResultEnum resultEnum) {
        isTrue(!judgment, resultEnum);
    }

    /**
     * 当条件判断为真时，抛出异常
     * @param judgment 需要判断的条件
     * @param resultEnum 异常信息描述
     */
    public static void isTrue(boolean judgment, ResultEnum resultEnum) {
        if (judgment) {
            message(resultEnum);
        }
    }

    /**
     * 当对象为空时，抛出异常
     * @param obj 传输对象
     * @param resultEnum 异常信息描述
     */
    public static void isNull(Object obj, ResultEnum resultEnum) {
        if (Objects.isNull(obj)) {
            message(resultEnum);
        }
    }

    /**
     * 当对象不为空时，抛出异常
     * @param obj 传输对象
     * @param resultEnum 异常信息描述
     */
    public static void isNotNull(Object obj, ResultEnum resultEnum) {
        if (!Objects.isNull(obj)) {
            message(resultEnum);
        }
    }

}
