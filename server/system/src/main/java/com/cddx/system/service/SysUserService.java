package com.cddx.system.service;

import com.alibaba.fastjson.JSONObject;
import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.enums.Status;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.bean.BeanUtils;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.service.BaseService;
import com.cddx.model.base.LoginUser;
import com.cddx.model.entity.SysUser;
import com.cddx.model.entity.SysUserDept;
import com.cddx.model.entity.SysUserRole;
import com.cddx.system.domain.dto.AddUserDto;
import com.cddx.system.domain.dto.EditUserDto;
import com.cddx.system.domain.dto.ListUserDto;
import com.cddx.system.domain.vo.UserVo;
import com.cddx.system.mapper.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 用户service
 *
 * @author 范劲松
 */
@Service
public class SysUserService extends BaseService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private SysUserDeptMapper userDeptMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private PermissionService permissionService;

    /**
     * 查询用户列表
     *
     * @param dto 传输类
     * @return 用户列表
     */
    public TableDataInfo list(ListUserDto dto) {
        startPage();
        List<UserVo> vos = sysUserMapper.queryUserList(dto);
        return getDataTable(vos);
    }

    /**
     * 获取用户信息通过用户id
     *
     * @param userId 用户id
     * @return 结果
     */
    public UserVo getUserInfoById(Long userId) {
        UserVo userVo = sysUserMapper.queryUserByUserId(userId);
        if (StringUtils.isNull(userVo)) {
            throw new CustomException(ResultEnum.USER_NOT_FOUND);
        }
        if (Status.DISABLED.eq(userVo.getStatus())) {
            throw new CustomException(ResultEnum.USER_STATUS_IS_DISABLED);
        }
        return userVo;
    }

    /**
     * 通过手机号查询用户信息
     *
     * @param phone 手机号
     * @return 用户信息
     */
    public SysUser queryLoginUserByPhone(String phone) {
        SysUser sysUser = sysUserMapper.selectUserByPhone(phone);
        if (StringUtils.isNull(sysUser)) {
            throw new CustomException(ResultEnum.USER_NOT_FOUND);
        }
        if (Status.DISABLED.eq(sysUser.getStatus())) {
            throw new CustomException(ResultEnum.USER_STATUS_IS_DISABLED);
        }
        return sysUser;
    }

    /**
     * 添加用户
     *
     * @param dto      添加实体
     * @param createId 创建者
     */
    @Transactional(rollbackFor = Exception.class)
    public void addUser(AddUserDto dto, Long createId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyBeanNotNull2Bean(dto, sysUser);
        // 检测用户是否存在
        if (sysUserMapper.selectExistByUser(sysUser).size() > 0) {
            throw new CustomException(ResultEnum.USER_IS_FOUND);
        }
        sysUser.setCreateId(createId);
        sysUser.setCreateTime(System.currentTimeMillis() / 1000);
        sysUser.setDelFlag(false);
        sysUserMapper.insert(sysUser);
        // 创建部门关联
        insertDept(sysUser.getId(), dto.getDeptId(), dto.getPosition());
        // 创建角色关联
        insertRole(sysUser.getId(), dto.getRoleId());
    }

    /**
     * 编辑用户信息
     *
     * @param dto          编辑实体
     * @param updateUserId 创建者
     */
    @Transactional(rollbackFor = Exception.class)
    public void editUser(EditUserDto dto, Long updateUserId) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyBeanNotNull2Bean(dto, sysUser);
        // 检测输入信息是否存在
        List<SysUser> existUsers = sysUserMapper.selectExistByUser(sysUser);
        // 移除它自己
        existUsers.removeIf(user -> user.getId().equals(dto.getId()));
        // 检测用户是否存在
        if (sysUserMapper.selectExistByUser(sysUser).size() > 0) {
            throw new CustomException(ResultEnum.USER_IS_FOUND);
        }
        sysUser.setLastUpdateId(updateUserId);
        sysUser.setLastUpdateTime(System.currentTimeMillis() / 1000);
        sysUserMapper.updateById(sysUser);
        // 删除部门关联
        userDeptMapper.deleteDeptByUserId(dto.getId());
        // 删除角色关联
        userRoleMapper.deleteRoleByUserId(dto.getId());
        // 创建部门关联
        insertDept(dto.getId(), dto.getDeptId(), dto.getPosition());
        // 创建角色关联
        insertRole(dto.getId(), dto.getRoleId());
    }

    /**
     * 关联部门及职位信息
     *
     * @param userId   用户
     * @param deptId   部门
     * @param position 职位
     */
    public void insertDept(Long userId, Long deptId, String position) {
        // 对加入的部门存在性校验
        boolean exist = deptMapper.existDeptById(deptId);
        if (!exist) {
            throw new CustomException(ResultEnum.DEPARTMENT_IS_NOT_FOUND);
        }
        SysUserDept sysUserDept = new SysUserDept();
        sysUserDept.setUserId(userId);
        sysUserDept.setDeptId(deptId);
        sysUserDept.setPosition(position);
        userDeptMapper.insert(sysUserDept);
    }

    /**
     * 关联角色
     *
     * @param userId 用户id
     * @param roleId 角色id
     */
    public void insertRole(Long userId, Long roleId) {
        // 对加入的角色存在性校验
        boolean exist = roleMapper.existRoleById(roleId);
        if (!exist) {
            throw new CustomException(ResultEnum.ROLE_IS_NOT_FOUND);
        }
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        userRoleMapper.insert(sysUserRole);
    }

    /**
     * 获取登录用户信息
     *
     * @param loginUser 登录用户
     * @return 结果
     */
    public JSONObject getInfo(LoginUser loginUser) {
        Long userId = loginUser.getSysUser().getId();
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);
        JSONObject ajax = new JSONObject();
        ajax.put("user", sysUserMapper.queryUserByUserId(userId));
        ajax.put("permissions", permissions);
        return ajax;
    }
}
