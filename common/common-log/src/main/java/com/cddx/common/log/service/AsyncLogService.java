package com.cddx.common.log.service;

import com.cddx.common.api.RemoteLogService;
import com.cddx.common.core.constant.SecurityConstants;
import com.cddx.common.core.model.entity.SysOperLog;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 异步调用日志服务
 *
 * @author 范劲松
 */
@Service
public class AsyncLogService {
    @Resource
    private RemoteLogService remoteLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(SysOperLog sysOperLog) {
        remoteLogService.saveLog(sysOperLog);
    }
}
