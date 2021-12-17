package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.domain.dto.ListRoleDto;
import com.cddx.domain.entity.SysRole;
import com.cddx.domain.vo.RoleListVo;
import com.cddx.domain.vo.RoleSelectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色
 *
 * @author 范劲松
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询角色信息通过id
     *
     * @param roleId 角色id
     * @return 角色信息
     */
    SysRole queryRoleById(Long roleId);

    /**
     * 角色下拉框选择列表
     *
     * @return 列表
     */
    List<RoleSelectVo> querySelectList();

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(Long userId);

    /**
     * 查询某角色是否存在
     *
     * @param roleId 角色id
     * @return 结果
     */
    boolean existRoleById(Long roleId);

    /**
     * 分页获取角色列表
     *
     * @param parameter 筛选条件参数
     * @return 角色列表
     */
    List<RoleListVo> queryList(ListRoleDto parameter);

    /**
     * 查询角色是否存在
     *
     * @param roleName 角色名
     * @return 结果
     */
    List<SysRole> checkExist(String roleName);
}
