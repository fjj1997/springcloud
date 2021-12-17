package com.cddx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cddx.domain.dto.ListDeptDto;
import com.cddx.domain.entity.SysDept;
import com.cddx.domain.vo.DeptListVo;
import com.cddx.domain.vo.DeptSelectVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 范劲松
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 分页查询部门列表
     *
     * @param parameter 查询筛选条件
     * @return 结果
     */
    List<DeptListVo> queryList(ListDeptDto parameter);

    /**
     * 通过部门id查询部门信息
     *
     * @param deptId 部门id
     * @return 结果
     */
    SysDept queryDeptById(Long deptId);

    /**
     * 查询某部门是否存在通过部门id
     *
     * @param deptId 部门id
     * @return 结果
     */
    boolean existDeptById(Long deptId);

    /**
     * 查询某部门是否存在通过部门名
     *
     * @param name 部门名
     * @return 结果
     */
    boolean existDeptByName(String name);

    /**
     * 角色下拉框选择列表
     *
     * @return 列表
     */
    List<DeptSelectVo> querySelectList();
}
