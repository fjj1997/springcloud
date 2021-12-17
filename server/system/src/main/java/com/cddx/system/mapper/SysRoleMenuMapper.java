package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.domain.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色菜单关联
 *
 * @author 范劲松
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 批量插入
     *
     * @param roleId 角色id
     * @param menuIds 菜单id
     */
    void insertBatch(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);

    /**
     * 查询该角色下面的绑定人数
     *
     * @param roleId 角色id
     * @return
     */
    Long countByRoleId(Long roleId);

    /**
     * 解除菜单与角色关联通过角色id
     *
     * @param id 角色id
     */
    void deleteByRoleId(Long id);
}
