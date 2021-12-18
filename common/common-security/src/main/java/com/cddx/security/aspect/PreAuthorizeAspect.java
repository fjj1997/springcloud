package com.cddx.security.aspect;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.exception.CustomException;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.model.base.LoginUser;
import com.cddx.security.annotation.PreAuthorize;
import com.cddx.security.service.TokenService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 自定义权限实现
 *
 * @author 范劲松
 */
@Aspect
@Component
public class PreAuthorizeAspect {
    @Resource
    private TokenService tokenService;

    /**
     * 所有权限标识
     */
    private static final String ALL_PERMISSION = "*:*:*";

    /**
     * 管理员角色权限标识
     */
    private static final String SUPER_ADMIN = "admin";

    /**
     * 数组为0时
     */
    private static final Integer ARRAY_EMPTY = 0;

    /**
     * 声明AOP签名
     */
    @Pointcut("@annotation(com.cddx.security.annotation.PreAuthorize)")
    public void pointcut() {
    }

    /**
     * 环绕切入
     *
     * @param point 切面对象
     * @return 底层方法执行后的返回值
     * @throws Throwable 底层方法抛出的异常
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 注解鉴权
        MethodSignature signature = (MethodSignature) point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
        if (annotation == null) {
            return point.proceed();
        }

        if (StringUtils.isNotEmpty(annotation.hasPermi())) {
            if (hasPermi(annotation.hasPermi())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        } else if (StringUtils.isNotEmpty(annotation.lacksPermi())) {
            if (lacksPermi(annotation.lacksPermi())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        } else if (ARRAY_EMPTY < annotation.hasAnyPermi().length) {
            if (hasAnyPermi(annotation.hasAnyPermi())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        } else if (StringUtils.isNotEmpty(annotation.hasRole())) {
            if (hasRole(annotation.hasRole())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        } else if (StringUtils.isNotEmpty(annotation.lacksRole())) {
            if (lacksRole(annotation.lacksRole())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        } else if (ARRAY_EMPTY < annotation.hasAnyRoles().length) {
            if (hasAnyRoles(annotation.hasAnyRoles())) {
                return point.proceed();
            }
            throw new CustomException(ResultEnum.NOT_PERMISSION_ERROR);
        }

        return point.proceed();
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission) {
        LoginUser userInfo = tokenService.getLoginUser();
        if (StringUtils.isNull(userInfo) || CollectionUtils.isEmpty(userInfo.getPermissions())) {
            return false;
        }
        return hasPermissions(userInfo.getPermissions(), permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    public boolean lacksPermi(String permission) {
        return !hasPermi(permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String[] permissions) {
        LoginUser userInfo = tokenService.getLoginUser();
        if (StringUtils.isNull(userInfo) || CollectionUtils.isEmpty(userInfo.getPermissions())) {
            return false;
        }
        Collection<String> authorities = userInfo.getPermissions();
        for (String permission : permissions) {
            if (permission != null && hasPermissions(authorities, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    public boolean hasRole(String role) {
        LoginUser userInfo = tokenService.getLoginUser();
        if (StringUtils.isNull(userInfo) || CollectionUtils.isEmpty(userInfo.getRoles())) {
            return false;
        }
        for (String roleKey : userInfo.getRoles()) {
            if (SUPER_ADMIN.equals(roleKey) || roleKey.equals(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    public boolean lacksRole(String role) {
        return !hasRole(role);
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 角色列表
     * @return 用户是否具有以下任意一个角色
     */
    public boolean hasAnyRoles(String[] roles) {
        LoginUser userInfo = tokenService.getLoginUser();
        if (StringUtils.isNull(userInfo) || CollectionUtils.isEmpty(userInfo.getRoles())) {
            return false;
        }
        for (String role : roles) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission  权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Collection<String> authorities, String permission) {
        return authorities.stream().filter(StringUtils::hasText)
                .anyMatch(x -> ALL_PERMISSION.contains(x) || PatternMatchUtils.simpleMatch(x, permission));
    }
}
