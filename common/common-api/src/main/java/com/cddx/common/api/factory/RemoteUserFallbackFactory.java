package com.cddx.common.api.factory;

import com.cddx.common.api.RemoteUserService;
import com.cddx.common.core.model.base.LoginUser;
import com.cddx.common.core.model.entity.SysUser;
import com.cddx.common.core.web.response.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 用户服务降级处理
 *
 * @author 范劲松
 */
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteUserFallbackFactory.class);

    @Override
    public RemoteUserService create(Throwable throwable) {
        log.error("用户服务调用失败:{}", throwable.getMessage());
        return new RemoteUserService() {
            @Override
            public R<LoginUser> getUserInfo(String username) {
                return R.fail("获取用户失败:" + throwable.getMessage());
            }

            @Override
            public R<Boolean> registerUserInfo(SysUser sysUser) {
                return R.fail("注册用户失败:" + throwable.getMessage());
            }
        };
    }
}
