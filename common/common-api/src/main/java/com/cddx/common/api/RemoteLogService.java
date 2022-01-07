package com.cddx.common.api;

import com.cddx.common.api.factory.RemoteLogFallbackFactory;
import com.cddx.common.core.constant.SecurityConstants;
import com.cddx.common.core.constant.ServiceNameConstants;
import com.cddx.common.core.model.entity.SysLogininfor;
import com.cddx.common.core.model.entity.SysOperLog;
import com.cddx.common.core.web.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 日志服务
 *
 * @author 范劲松
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteLogFallbackFactory.class)
public interface RemoteLogService {
    /**
     * 保存系统日志
     *
     * @param sysOperLog 日志实体
     * @return 结果
     */
    @PostMapping("/operlog")
    public R<Boolean> saveLog(@RequestBody SysOperLog sysOperLog);

    /**
     * 保存访问记录
     *
     * @param sysLogininfor 访问实体
     * @return 结果
     */
    @PostMapping("/logininfor")
    public R<Boolean> saveLogininfor(@RequestBody SysLogininfor sysLogininfor);
}
