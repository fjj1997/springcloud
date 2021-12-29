package com.cddx.system.domain.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 角色和菜单关联 sys_role_menu
 *
 * @author 范劲松
 */
@Data
@ToString
public class SysRoleMenu {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}
