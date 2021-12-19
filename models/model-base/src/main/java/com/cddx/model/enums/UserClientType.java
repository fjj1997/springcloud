package com.cddx.model.enums;

/**
 * 用户端口类型
 *
 * @author 范劲松
 */
public enum UserClientType {
    /**
     * 管理中台
     */
    MANAGE(999);

    /**
     * 客户端编码
     */
    private final Integer client;

    UserClientType(Integer client) {
        this.client = client;
    }

    public boolean eq(UserClientType type) {
        return this.client.equals(type.client);
    }
}
