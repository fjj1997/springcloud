package com.cddx.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 系统用户角色关联
 *
 * @author 范劲松
 */
@Data
@ToString
@Accessors(chain = true)
@TableName(value = "sys_user_role", autoResultMap = true)
public class SysUserRole {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 角色id
     */
    private Long roleId;

}
