package com.cddx.common.core.enums;

/**
 * 响应状态码枚举
 *
 * @author 范劲松
 */
public enum ResultEnum {

    /**
     * 枚举状态对应码
     * */
    ILLEGAL_OPERATION(-1, "非法操作"),

    SUCCESS_CODE(200, "操作成功"),
    LOGIN_SUCCESS(201, "登录成功"),
    SIGN_IN_SUCCESS(202, "注册成功"),
    SEND_VALID_CODE(205, "验证码已发送,请耐心等待~"),
    LOGIN_OUT_SUCCESS(207, "退出登录成功"),

    WARNING(300, "操作警告"),

    AOP_PARAM_ERROR(409, "参数绑定异常"),
    FILE_SIZE_LIMIT_ERROR(419, "文件过大"),
    OUT_DATE_VALIDATE_IMAGE(421, "校验图片已过期"),
    SYSTEM_DATA_ERROR(423, "禁止操作系统创建的数据"),
    INFO_NOT_FOUND(430, "未查询到相关信息"),

    IMPORT_DATA_IS_NULL(432, "导入的数据为空"),

    NO_TOKEN(1001, "未提供访问令牌"),
    INVALID_TOKEN(1002, "登录信息失效"),
    INVALID_CHECK_TOKEN(1003, "令牌验证失败"),
    NOT_PERMISSION_ERROR(1007, "无访问权限"),

    NO_HAVE_APP_ERROR(1131, "app不存在, 请先联系管理员申请app"),
    APP_SIGN_ERROR(1132, "签名出错"),

    UNKNOWN_ERROR(10000, "未知错误"),
    BUSY_BUSINESS(10001, "业务繁忙"),
    BUSINESS_ERROR(10002, "业务异常"),
    SERVICE_ERROR(10003, "服务异常"),
    DATA_ERROR(10004, "数据异常"),
    SIGN_DISABLE(10005, "暂停访问"),
    FIELD_VALIDATION_ERROR(10006, "数据校验异常"),
    DEFAULT_FAILED_MSG(10008, "短信服务繁忙，请稍后再请求"),

    GET_ERROR_VALIDATE_CODE(15000, "验证码获取失败"),
    OUT_DATE_VALIDATE_CODE(15001, "验证码已过期"),
    INVALID_VALIDATE_CODE(15002, "无效的验证码"),

    USER_NOT_FOUND(20001, "用户不存在"),
    TELEPHONE_NOT_SIGN_IN(20002, "该手机号尚未注册"),
    TELEPHONE_ALREADY_EXIST(20003, "手机号码已经存在"),
    ACCOUNT_OR_PWD_ERROR(20004, "用户名或密码错误"),
    NOT_BIND_PHONE(20005, "未绑定手机号"),
    USER_STATUS_IS_DISABLED(20006, "账号已停用"),
    USER_IS_FOUND(20007, "用户已存在"),
    DEPARTMENT_IS_NOT_FOUND(20030, "部门不存在"),
    DEPARTMENT_IS_FOUND(20031, "部门已存在"),
    DEPARTMENT_DONT_DELETE(20032, "禁止删除！请将人员移出部门后重试~"),
    ROLE_IS_NOT_FOUND(20050, "角色不存在"),
    ROLE_IS_FOUND(20051, "角色已存在"),
    ROLE_DONT_DELETE(20052, "禁止删除！请将人员移出角色后重试~"),



    ;

    /** 响应状态码 */
    private final Integer code;
    /** 响应信息内容 */
    private final String msg;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
