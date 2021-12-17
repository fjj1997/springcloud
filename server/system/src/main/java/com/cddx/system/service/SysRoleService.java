package com.cddx.system.service;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.enums.RolePermType;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.service.BaseService;
import com.cddx.model.base.BaseEntity;
import com.cddx.model.base.LoginUser;
import com.cddx.model.entity.SysRole;
import com.cddx.system.domain.dto.AddRoleDto;
import com.cddx.system.domain.dto.EditRoleDto;
import com.cddx.system.domain.dto.ListRoleDto;
import com.cddx.system.domain.vo.RoleListVo;
import com.cddx.system.domain.vo.RoleSelectVo;
import com.cddx.system.mapper.SysRoleMapper;
import com.cddx.system.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色
 *
 * @author 范劲松
 */
@Service
public class SysRoleService extends BaseService {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 角色下拉框选择列表
     *
     * @return 列表
     */
    public List<RoleSelectVo> querySelectList() {
        return sysRoleMapper.querySelectList();
    }

    /**
     * 获取某账号的角色权限
     *
     * @param userId 用户id
     * @return 结果
     */
    public List<SysRole> selectRolePermissionByUserId(Long userId) {
        List<SysRole> perms = sysRoleMapper.selectRolePermissionByUserId(userId);
        // 移除为空状态
        perms.removeIf(StringUtils::isNull);
        // 整理为set
        return perms;
    }

    /**
     * 获取角色列表
     *
     * @param dto 传输类
     * @return 角色列表
     */
    public TableDataInfo list(ListRoleDto dto) {
        startPage();
        List<RoleListVo> vos = sysRoleMapper.queryList(dto);
        return getDataTable(vos);
    }

    /**
     * 创建角色
     *
     * @param dto 传输数据
     * @param loginUser 登录用户
     */
    public void add(AddRoleDto dto, LoginUser loginUser) {
        // 检测角色是否存在(不允许同名角色)
        List<SysRole> roles = sysRoleMapper.checkExist(dto.getRoleName());
        roles.removeIf(BaseEntity::isDelFlag);
        if (roles.size() > 0) {
            throw new CustomException(ResultEnum.ROLE_IS_FOUND);
        }
        // 创建角色
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(dto.getRoleName());
        sysRole.setRoleSort(0);
        // 判断给的类型是不是正常的权限类型，否则提供配置类型
        if (RolePermType.isType(dto.getType())) {
            sysRole.setType(dto.getType());
        } else {
            sysRole.setType(RolePermType.CONFIGURE.getType());
        }
        sysRole.setCreateId(loginUser.getSysUser().getId());
        sysRole.setCreateTime(System.currentTimeMillis() / 1000);
        sysRole.setDelFlag(false);
        sysRoleMapper.insert(sysRole);
        // 如果角色类型是全部菜单，就没必要关联菜单了
        if (RolePermType.CONFIGURE.eq(dto.getType())) {
            // 创建菜单角色关联
            insertRoleMenu(sysRole.getId(), dto.getMenuIds());
        }
    }

    /**
     * 编辑角色
     *
     * @param dto 传输数据
     * @param loginUser 登录用户
     */
    public void edit(EditRoleDto dto, LoginUser loginUser) {
        // 检查角色是否存在
        SysRole role = sysRoleMapper.selectById(dto.getId());
        if (StringUtils.isNull(role) || role.isDelFlag()) {
            throw new CustomException(ResultEnum.ROLE_IS_NOT_FOUND);
        }
        // 检测角色是否存在(不允许同名角色)
        List<SysRole> roles = sysRoleMapper.checkExist(dto.getRoleName());
        // 移除它自己和已删除的角色
        roles.removeIf(item -> item.isDelFlag() || item.getId().equals(dto.getId()));
        if (roles.size() > 0) {
            throw new CustomException(ResultEnum.ROLE_IS_FOUND);
        }
        role.setRoleName(dto.getRoleName());
        if (RolePermType.isType(dto.getType())) {
            role.setType(dto.getType());
        }
        role.setLastUpdateId(loginUser.getSysUser().getId());
        role.setLastUpdateTime(System.currentTimeMillis() / 1000);
        // 提交数据
        sysRoleMapper.updateById(role);
        // 删除菜单关联
        sysRoleMenuMapper.deleteByRoleId(role.getId());
        // 如果角色类型是全部菜单，就没必要关联菜单了
        if (RolePermType.CONFIGURE.eq(dto.getType())) {
            // 创建菜单角色关联
            insertRoleMenu(role.getId(), dto.getMenuIds());
        }
    }

    /**
     * 删除角色
     *
     * @param id 角色id
     */
    public void delete(Long id) {
        // 判断角色中时候还有人员
        if (sysRoleMenuMapper.countByRoleId(id) > 0) {
            // 如果还有人，禁止删除
            throw new CustomException(ResultEnum.ROLE_DONT_DELETE);
        }
        // 执行删除 (逻辑删除)
        SysRole role = sysRoleMapper.selectById(id);
        role.setDelFlag(true);
        sysRoleMapper.updateById(role);
    }

    /**
     * 创建菜单角色关联
     *
     * @param roleId 角色id
     * @param menuIds 菜单ids
     */
    public void insertRoleMenu(Long roleId, List<Long> menuIds) {
        if (menuIds.size() > 0) {
            // 先对list去重
            menuIds = menuIds.stream().distinct().collect(Collectors.toList());
            // 批量加入
            sysRoleMenuMapper.insertBatch(roleId, menuIds);
        }
    }
}
