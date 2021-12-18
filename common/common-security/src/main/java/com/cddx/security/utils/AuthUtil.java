package com.cddx.security.utils;

import com.cddx.model.base.LoginUser;
import com.cddx.security.auth.AuthLogic;

/**
 * 权限工具
 *
 * @author 范劲松
 */
public class AuthUtil {
    /**
     * 底层的 AuthLogic 对象
     */
    public static AuthLogic authLogic = new AuthLogic();

    /**
     * 会话注销
     */
    public static void logout() {
        authLogic.logout();
    }

    /**
     * 会话注销，根据指定Token
     *
     * @param token 指定token
     */
    public static void logoutByToken(String token) {
        authLogic.logoutByToken(token);
    }

    /**
     * 检验当前会话是否已经登录，如未登录，则抛出异常
     */
    public static void checkLogin() {
        authLogic.checkLogin();
    }

    /**
     * 获取当前登录用户信息
     */
    public static LoginUser getLoginUser(String token) {
        return authLogic.getLoginUser(token);
    }

    /**
     * 验证当前用户有效期
     */
    public static void verifyLoginUserExpire(LoginUser loginUser) {
        authLogic.verifyLoginUserExpire(loginUser);
    }

    /**
     * 当前账号是否含有指定角色标识, 返回true或false
     *
     * @param role 角色标识
     * @return 是否含有指定角色标识
     */
    public static boolean hasRole(String role) {
        return authLogic.hasRole(role);
    }

    /**
     * 当前账号是否含有指定角色标识, 如果验证未通过，则抛出异常: NotRoleException
     *
     * @param role 角色标识
     */
    public static void checkRole(String role) {
        authLogic.checkRole(role);
    }
}
