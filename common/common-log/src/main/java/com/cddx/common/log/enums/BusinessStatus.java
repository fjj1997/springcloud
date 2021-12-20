package com.cddx.common.log.enums;

/**
 * 操作状态
 *
 * @author 范劲松
 */
public enum BusinessStatus {
    /**
     * 成功
     */
    SUCCESS('0'),

    /**
     * 失败
     */
    FAIL('1');

    private final char status;

    BusinessStatus(char status) {
        this.status = status;
    }

    public char getStatus() {
        return status;
    }
}
