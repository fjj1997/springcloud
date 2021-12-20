package com.cddx.system.controller;

import com.cddx.common.core.constant.UserConstants;
import com.cddx.common.core.enums.UserClientType;
import com.cddx.common.core.model.base.LoginUser;
import com.cddx.common.core.model.entity.SysRole;
import com.cddx.common.core.model.entity.SysUser;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.poi.ExcelUtil;
import com.cddx.common.core.web.controller.BaseController;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.core.web.response.R;
import com.cddx.common.security.annotation.PreAuthorize;
import com.cddx.common.security.utils.SecurityUtils;
import com.cddx.system.service.*;
import lombok.extern.java.Log;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author 范劲松
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController {
    @Resource
    private SysUserService userService;

    @Resource
    private SysRoleService roleService;

    @Resource
    private SysPostService postService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private SysConfigService configService;

    /**
     * 获取用户列表
     */
    @PreAuthorize(hasPermi = "system:user:list", client = UserClientType.MANAGE)
    @GetMapping("/list")
    public TableDataInfo list(SysUser user) {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    // @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:user:export", client = UserClientType.MANAGE)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user) {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    // @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize(hasPermi = "system:user:import", client = UserClientType.MANAGE)
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        Long operId = SecurityUtils.getUserId();
        String message = userService.importUser(userList, updateSupport, operId);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 获取当前用户信息
     */
    // @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username) {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser)) {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser.getUserId());
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser.getUserId());
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }

    /**
     * 注册用户信息
     */
    // @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser) {
        String username = sysUser.getUserName();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser")))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(username))) {
            return R.fail("保存用户'" + username + "'失败，注册账号已存在");
        }
        return R.ok(userService.registerUser(sysUser));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        Long userId = SecurityUtils.getUserId();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(userId);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(userId);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", userService.selectUserById(userId));
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize(hasPermi = "system:user:query", client = UserClientType.MANAGE)
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            ajax.put(AjaxResult.DATA_TAG, userService.selectUserById(userId));
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", roleService.selectRoleListByUserId(userId));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize(hasPermi = "system:user:add", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize(hasPermi = "system:user:edit", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize(hasPermi = "system:user:remove", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, SecurityUtils.getUserId())) {
            return AjaxResult.error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize(hasPermi = "system:user:edit", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize(hasPermi = "system:user:edit", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) {
        userService.checkUserAllowed(user);
        user.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize(hasPermi = "system:user:query", client = UserClientType.MANAGE)
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize(hasPermi = "system:user:edit", client = UserClientType.MANAGE)
    // @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }
}
