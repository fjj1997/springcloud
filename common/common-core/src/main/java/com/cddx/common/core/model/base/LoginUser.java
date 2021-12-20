package com.cddx.common.core.model.base;

import com.cddx.common.core.enums.UserClientType;
import com.cddx.common.core.model.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录用户
 *
 * @author 范劲松
 */
@Data
public class LoginUser implements Serializable {

    /**
     * 用户唯一标识
     */
    private String token;

    /**
     * 用户名id
     */
    private Long userid;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;

    /**
     * 用户信息
     */
    private SysUser sysUser;

    private UserClientType clientType;

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
        this.clientType = UserClientType.MANAGE;
    }
}
