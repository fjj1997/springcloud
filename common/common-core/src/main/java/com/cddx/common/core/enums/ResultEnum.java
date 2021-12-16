package com.cddx.common.core.enums;

/**
 * 响应状态码枚举
 *
 * @author 范劲松
 */
public enum ResultEnum {

    /**
     * 枚举状态对应码
     * 200-299 成功
     * 300-399 警告
     * 400-499 错误
     * */
    SUCCESS_CODE(200, "操作成功"),
    LOGIN_SUCCESS(201, "登录成功"),
    SIGN_IN_SUCCESS(202, "注册成功"),
    ORG_NOT_SIGN_IN(203, "该公司/机构尚未注册"),
    TELEPHONE_NOT_SIGN_IN(204, "该手机号尚未注册"),
    SEND_VALID_CODE(205, "验证码已发送,请耐心等待。"),
    RECEIVING_VALID_CODE(206, "验证码已发送,请注意查收"),
    LOGIN_OUT_SUCCESS(207, "退出登录成功"),
    UPDATE_PASSWORD_TIME_LIMIT(208, "验证成功,请在5分钟内完成密码修改!"),
    TELEPHONE_BIND_USER(209, "查询到手机号绑定用户"),
    WARNING(300, "操作警告"),
    BUSINESS_ERROR(401, "业务异常"),
    SERVICE_ERROR(402, "服务异常"),
    INVALID_TOKEN(403, "登录信息失效"),
    NO_TOKEN(404, "没有token"),
    USER_NOT_FOUND(405, "用户不存在"),
    DATA_ERROR(406, "数据异常"),
    SIGN_DISABLE(407, "暂停访问"),
    FIELD_VALIDATION_ERROR(408, "数据校验异常"),
    AOP_PARAM_ERROR(409, "参数绑定异常"),
    NOT_BIND_PHONE(410, "未绑定手机号"),
    ENOUGH_ORG_ERROR(411, "不可以添加更多的组织架构了"),
    ORG_NOT_FOUND(412, "公司/机构不存在"),
    ACCOUNT_OR_PWD_ERROR(413, "用户名或密码错误"),
    ORG_ALREADY_EXIST(414, "该公司/机构已经存在"),
    TELEPHONE_ALREADY_EXIST(415, "手机号码已经存在"),
    OUT_DATE_VALIDATE_CODE(416, "验证码已过期"),
    INVALID_VALIDATE_CODE(417, "无效的验证码"),
    GET_ERROR_VALIDATE_CODE(418, "验证码获取失败"),
    FILE_SIZE_LIMIT_ERROR(419, "文件过大"),
    DEFAULT_FAILED_MSG(420, "短信服务繁忙，请稍后再请求"),
    OUT_DATE_VALIDATE_IMAGE(421, "校验图片已过期"),
    DEVICE_CODE_REPEAT(422, "检测到设备编码重复"),
    SYSTEM_DATA_ERROR(423, "禁止操作系统创建的数据"),
    BUSY_BUSINESS(424, "业务繁忙"),
    INFO_NOT_FOUND(430, "未查询到相关信息"),
    PRIMARY_KEY_IS_NULL(431, "主键ID为空"),
    IMPORT_DATA_IS_NULL(432, "导入的数据为空"),
    CAN_NOT_CREATE_MORE_DATA(433,"不可以再添加更多的数据了"),
    ILLEGAL_OPERATION(501,"非法操作"),
    UNKNOWN_ERROR(1000, "未知错误"),
    NO_HAVE_APP_ERROR(1031, "app不存在, 请先联系管理员申请app"),
    APP_SIGN_ERROR(1041, "签名出错");

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
