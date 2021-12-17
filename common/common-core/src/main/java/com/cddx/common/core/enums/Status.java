package com.cddx.common.core.enums;

/**
 * 状态
 *
 * @author 范劲松
 */
public enum Status {
    /**
     * 启用
     */
    ENABLE('0'),

    /**
     * 停用
     */
    DISABLED('1');

    private final char status;

    Status(char status) {
        this.status = status;
    }

    public boolean eq(char status) {
        return this.status == status;
    }
}
