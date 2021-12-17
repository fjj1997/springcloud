package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.model.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联
 *
 * @author 范劲松
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 通过用户id删除角色id与用户id的关联
     *
     * @param userId 用户id
     * @return 结果
     */
    int deleteRoleByUserId(Long userId);

}
