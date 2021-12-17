package com.cddx.common.core.enums;

/**
 * 菜单类型
 *
 * @author 范劲松
 */
public enum MenuType {
    /**
     * 目录
     */
    DIR('M'),

    /**
     * 菜单类型
     */
    MENU('C'),

    /**
     * 按钮类型
     */
    BUTTON('F');

    private final char type;

    MenuType(char type) {
        this.type = type;
    }

    public boolean eq(char type) {
        return this.type == type;
    }
}
