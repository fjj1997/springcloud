package com.cddx.common.core.enums;

/**
 * 角色权限
 *
 * @author 范劲松
 */
public enum RolePermType {
    /**
     * 配置权限
     */
    CONFIGURE('0'),

    /**
     * 全部权限
     */
    ALL('1');

    /**
     * 类型
     */
    private final char type;

    RolePermType(char type) {
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public static boolean isType(char type) {
        RolePermType[] values = RolePermType.values();
        for (RolePermType value : values) {
            if (value.eq(type)) {
                return true;
            }
        }
        return false;
    }

    public boolean eq(char type) {
        return this.type == type;
    }

}
