package com.cddx.system.service;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.bean.BeanUtils;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.service.BaseService;
import com.cddx.model.base.LoginUser;
import com.cddx.model.entity.SysDept;
import com.cddx.system.domain.dto.AddDeptDto;
import com.cddx.system.domain.dto.EditDeptDto;
import com.cddx.system.domain.dto.ListDeptDto;
import com.cddx.system.domain.vo.DeptListVo;
import com.cddx.system.domain.vo.DeptSelectVo;
import com.cddx.system.mapper.SysDeptMapper;
import com.cddx.system.mapper.SysUserDeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门
 *
 * @author 范劲松
 */
@Service
public class SysDeptService extends BaseService {
    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserDeptMapper userDeptMapper;

    /**
     * 分页查询部门列表
     *
     * @param dto 查询传输条件
     * @return 结果
     */
    public TableDataInfo list(ListDeptDto dto) {
        startPage();
        List<DeptListVo> vos = sysDeptMapper.queryList(dto);
        return getDataTable(vos);
    }

    /**
     * 下拉选择框
     *
     * @return 结果
     */
    public List<DeptSelectVo> querySelectList() {
        return sysDeptMapper.querySelectList();
    }

    /**
     * 添加部门
     *
     * @param dto 传输类
     * @param loginUser 登录用户
     */
    public void add(AddDeptDto dto, LoginUser loginUser) {
        // 检查部门名是否存在
        if (sysDeptMapper.existDeptByName(dto.getName())) {
            // 抛出部门已存在
            throw new CustomException(ResultEnum.DEPARTMENT_IS_FOUND);
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyBeanNotNull2Bean(dto, sysDept);
        sysDept.setCreateId(loginUser.getSysUser().getId());
        sysDept.setCreateTime(System.currentTimeMillis() / 1000);
        sysDept.setDelFlag(false);
        sysDeptMapper.insert(sysDept);
    }

    /**
     * 编辑部门
     *
     * @param dto 传输类
     * @param loginUser 登录用户
     */
    public void edit(EditDeptDto dto, LoginUser loginUser) {
        // 检查当前修改的部门是否存在
        SysDept dept = sysDeptMapper.selectById(dto.getId());
        if (StringUtils.isNull(dept) || dept.isDelFlag()) {
            // 抛出部门不存在
            throw new CustomException(ResultEnum.DEPARTMENT_IS_NOT_FOUND);
        }
        // 检查部门名是否存在
        if (sysDeptMapper.existDeptByName(dto.getName())) {
            // 抛出部门已存在
            throw new CustomException(ResultEnum.DEPARTMENT_IS_FOUND);
        }
        dept.setName(dto.getName());
        dept.setLastUpdateId(loginUser.getSysUser().getId());
        dept.setLastUpdateTime(System.currentTimeMillis() / 1000);
        sysDeptMapper.updateById(dept);
    }

    /**
     * 删除部门
     *
     * @param id 部门id
     */
    public void delete(Long id) {
        // 判断部门中时候还有人员
        if (userDeptMapper.countByDeptId(id) > 0) {
            // 如果还有人，禁止删除
            throw new CustomException(ResultEnum.DEPARTMENT_DONT_DELETE);
        }
        // 执行删除 (逻辑删除)
        SysDept sysDept = sysDeptMapper.selectById(id);
        sysDept.setDelFlag(true);
        sysDeptMapper.updateById(sysDept);
    }
}
