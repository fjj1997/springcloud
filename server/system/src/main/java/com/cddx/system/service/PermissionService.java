package com.cddx.system.service;

import com.cddx.common.core.enums.RolePermType;
import com.cddx.model.entity.SysRole;
import com.cddx.system.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 获取权限
 *
 * @author 范劲松
 */
@Service
public class PermissionService {

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysMenuService menuService;

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 获取菜单数据权限
     *
     * @param userId 用户Id
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(Long userId) {
        // 管理员拥有所有权限
        if (hasAllPermByUserId(userId)) {
            Set<String> perms = new HashSet<>();
            perms.add("*:*:*");
            return perms;
        } else {
            return (menuService.selectMenuPermsByUserId(userId));
        }
    }

    /**
     * 判断某角色是否含有所有的权限
     *
     * @param userId 用户id
     * @return 结果
     */
    public boolean hasAllPermByUserId(Long userId) {
        // 内置账号
        if (1L == userId) {
            return true;
        }
        List<SysRole> sysRoles = roleService.selectRolePermissionByUserId(userId);
        // 遍历角色权限
        for (SysRole role : sysRoles) {
            if (RolePermType.ALL.eq(role.getType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断某角色是否含有所有的权限
     *
     * @param roleId 角色id
     * @return 结果
     */
    public boolean hasAllPermByRoleId(Long roleId) {
        SysRole role = sysRoleMapper.selectById(roleId);
        return RolePermType.ALL.eq(role.getType());
    }

}
