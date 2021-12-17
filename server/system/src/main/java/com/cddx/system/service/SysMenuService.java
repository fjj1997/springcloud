package com.cddx.system.service;

import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.bean.BeanUtils;
import com.cddx.common.core.utils.menu.MenuUtils;
import com.cddx.common.core.utils.tree.TreeUtil;
import com.cddx.model.base.LoginUser;
import com.cddx.model.entity.SysMenu;
import com.cddx.model.vo.menu.MenuVo;
import com.cddx.model.vo.menu.RouterVo;
import com.cddx.system.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 系统菜单
 *
 * @author 范劲松
 */
@Service
public class SysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private PermissionService permissionService;

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId) {
        List<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        // 移除空字符
        perms.removeIf(StringUtils::isEmpty);
        // 直接转为HashSet返回
        return new HashSet<>(perms);
    }

    /**
     * 获取某登录账号的路由菜单
     *
     * @return 路由菜单
     */
    @SuppressWarnings("all")
    public List<RouterVo> getRouter(LoginUser loginUser) {
        List<RouterVo> routerVos = TreeUtil.buildTree(selectRouterMenuList(loginUser.getSysUser().getId()));
        routerVos = routerVos.stream().peek(first -> {
            List children = first.getChildren();
            for (int i = 0; i < children.size(); i++) {
                RouterVo vo = (RouterVo) children.get(i);
                children.addAll(vo.getChildren());
            }
        }).collect(Collectors.toList());
        return routerVos;
    }

    /**
     * 查询当前账号下所有路由菜单
     *
     * @param userId 用户id
     * @return 结果
     */
    public List<RouterVo> selectRouterMenuList(Long userId) {
        List<SysMenu> list;
        // 根据权限拿取菜单列表
        if (permissionService.hasAllPermByUserId(userId)) {
            // 如果具有全部权限，则拿取所有菜单
            list = sysMenuMapper.selectAllRouteMenu();
        } else {
            // 否则就只享有它所拥有的部分
            list = sysMenuMapper.selectRouteMenuByUserId(userId);
        }
        return MenuUtils.buildMenus(list);
    }

    /**
     * 通过角色id获取菜单
     *
     * @param roleId 角色id
     * @return 结果
     */
    public List<MenuVo> getMenuByRole(Long roleId) {
        List<SysMenu> list;
        if (permissionService.hasAllPermByRoleId(roleId)) {
            list = sysMenuMapper.selectAllMenu();
        } else {
            list = sysMenuMapper.selectMenuByRoleId(roleId);
        }
        // 先转化为menu vo，再构建树形结构
        return TreeUtil.buildTree(list.stream().map(menu -> {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyBeanNotNull2Bean(menu, menuVo);
            return menuVo;
        }).collect(Collectors.toList()));
    }

    /**
     * 获取指定用户的所有权限菜单
     *
     * @param userId 用户id
     * @return 结果
     */
    public List<MenuVo> getMenuByUserId(Long userId) {
        List<SysMenu> list;
        // 根据权限拿取菜单列表
        if (permissionService.hasAllPermByUserId(userId)) {
            // 如果具有全部权限，则拿取所有菜单
            list = sysMenuMapper.selectAllRouteMenu();
        } else {
            // 否则就只享有它所拥有的部分
            list = sysMenuMapper.selectRouteMenuByUserId(userId);
        }
        // 先转化为menu vo，再构建树形结构
        return TreeUtil.buildTree(list.stream().map(menu -> {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyBeanNotNull2Bean(menu, menuVo);
            return menuVo;
        }).collect(Collectors.toList()));
    }
}
