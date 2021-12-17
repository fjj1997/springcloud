package com.cddx.system.mapper;

import com.cddx.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 范劲松
 */
@Mapper
public interface SysMenuMapper  {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 通过用户id查询某个用户的菜单
     *
     * @param userId 用户id
     * @return 结果
     */
    List<SysMenu> selectRouteMenuByUserId(Long userId);

    /**
     * 提取所有菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> selectAllRouteMenu();

    /**
     * 通过用户id查询某个用户的菜单
     *
     * @param roleId 角色id
     * @return 结果
     */
    List<SysMenu> selectMenuByRoleId(Long roleId);

    /**
     * 提取所有菜单
     *
     * @return 菜单列表
     */
    List<SysMenu> selectAllMenu();
}
