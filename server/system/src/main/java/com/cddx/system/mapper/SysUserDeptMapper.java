package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.model.entity.SysUserDept;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *
 * @author 范劲松
 */
@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * 查询部门中加入的人数
     *
     * @param deptId 部门id
     * @return 人数
     */
    Long countByDeptId(Long deptId);

    /**
     * 通过用户id删除部门id与用户id的关联
     *
     * @param userId 用户id
     * @return 结果
     */
    int deleteDeptByUserId(Long userId);

}
