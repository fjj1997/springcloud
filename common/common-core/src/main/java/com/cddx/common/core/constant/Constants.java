package com.cddx.common.core.constant;

import com.cddx.common.core.enums.ResultEnum;

/**
 * 通用常量信息
 *
 * @author 范劲松
 */
public class Constants {

    /** 英文逗号 */
    public static final String DELIMITER_COMMA = ",";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi://";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = ResultEnum.SUCCESS_CODE.getCode();

    /**
     * 失败标记
     */
    public static final Integer FAIL = ResultEnum.SERVICE_ERROR.getCode();

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌有效期（分钟）
     */
    public final static long TOKEN_EXPIRE = 720;

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /** 删除标记--是 */
    public static final Boolean DEL_FLAG_TRUE = true;
    /** 删除标记--否 */
    public static final Boolean DEL_FLAG_FALSE = false;

    /** 统计方式--按小时 */
    public static final String COUNT_BY_HOURS = "hour";
    /** 统计方式--按天 */
    public static final String COUNT_BY_DAY = "day";
    /** 统计方式--按月 */
    public static final String COUNT_BY_MONTH = "month";
}
