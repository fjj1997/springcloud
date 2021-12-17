package com.cddx.system.service;

import com.cddx.base.LoginUser;
import com.cddx.base.PageGrid;
import com.cddx.domain.dto.AddDeptDto;
import com.cddx.domain.dto.EditDeptDto;
import com.cddx.domain.dto.ListDeptDto;
import com.cddx.domain.entity.SysDept;
import com.cddx.domain.vo.DeptListVo;
import com.cddx.domain.vo.DeptSelectVo;
import com.cddx.exception.http.ServerErrorException;
import com.cddx.mapper.SysDeptMapper;
import com.cddx.mapper.SysUserDeptMapper;
import com.cddx.utils.BeanUtils;
import com.cddx.utils.MyPage;
import com.cddx.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门
 *
 * @author 范劲松
 */
@Service
public class SysDeptService {
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
    public MyPage<DeptListVo> list(PageGrid<ListDeptDto> dto) {
        Integer pageSize = dto.getPageSize();
        Integer pageNo = dto.getPageNo();
        Page<DeptListVo> page = PageHelper.startPage(pageNo, pageSize);
        sysDeptMapper.queryList(dto.getParameter());
        MyPage<DeptListVo> myPage = new MyPage<>(page);
        myPage.setList(page);
        return myPage;
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
            throw new ServerErrorException(20031);
        }
        SysDept sysDept = new SysDept();
        BeanUtils.copyPropertiesIgnoreNull(dto, sysDept);
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
            throw new ServerErrorException(20030);
        }
        // 检查部门名是否存在
        if (sysDeptMapper.existDeptByName(dto.getName())) {
            // 抛出部门已存在
            throw new ServerErrorException(20031);
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
            throw new ServerErrorException(20032);
        }
        // 执行删除 (逻辑删除)
        SysDept sysDept = sysDeptMapper.selectById(id);
        sysDept.setDelFlag(true);
        sysDeptMapper.updateById(sysDept);
    }
}
