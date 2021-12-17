package com.cddx.system.service;

import com.cddx.base.BaseEntity;
import com.cddx.base.LoginUser;
import com.cddx.base.PageGrid;
import com.cddx.domain.dto.AddRoleDto;
import com.cddx.domain.dto.EditRoleDto;
import com.cddx.domain.dto.ListRoleDto;
import com.cddx.domain.entity.SysRole;
import com.cddx.domain.vo.RoleListVo;
import com.cddx.domain.vo.RoleSelectVo;
import com.cddx.enums.RolePermType;
import com.cddx.exception.http.ServerErrorException;
import com.cddx.mapper.SysRoleMapper;
import com.cddx.mapper.SysRoleMenuMapper;
import com.cddx.utils.MyPage;
import com.cddx.utils.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
public class SysRoleService {
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
    public MyPage<RoleListVo> list(PageGrid<ListRoleDto> dto) {
        Integer pageSize = dto.getPageSize();
        Integer pageNo = dto.getPageNo();
        Page<RoleListVo> page = PageHelper.startPage(pageNo, pageSize);
        sysRoleMapper.queryList(dto.getParameter());
        MyPage<RoleListVo> myPage = new MyPage<>(page);
        myPage.setList(page);
        return myPage;
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
            throw new ServerErrorException(20051);
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
            throw new ServerErrorException(20050);
        }
        // 检测角色是否存在(不允许同名角色)
        List<SysRole> roles = sysRoleMapper.checkExist(dto.getRoleName());
        // 移除它自己和已删除的角色
        roles.removeIf(item -> item.isDelFlag() || item.getId().equals(dto.getId()));
        if (roles.size() > 0) {
            throw new ServerErrorException(20051);
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
            throw new ServerErrorException(20052);
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
