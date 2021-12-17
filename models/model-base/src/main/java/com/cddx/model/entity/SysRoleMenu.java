package com.cddx.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色菜单关联
 *
 * @author 范劲松
 */
@Data
@ToString
@Accessors(chain = true)
@TableName(value = "sys_role_menu", autoResultMap = true)
public class SysRoleMenu {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}
