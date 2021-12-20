package com.cddx.system.controller;

import com.cddx.common.core.enums.UserClientType;
import com.cddx.common.core.utils.poi.ExcelUtil;
import com.cddx.common.core.web.controller.BaseController;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.security.annotation.PreAuthorize;
import com.cddx.system.domain.entity.SysOperLog;
import com.cddx.system.service.SysOperLogService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 操作日志记录
 *
 * @author 范劲松
 */
@RestController
@RequestMapping("/operlog")
public class SysOperlogController extends BaseController {
    @Resource
    private SysOperLogService operLogService;

    @PreAuthorize(hasPermi = "system:operlog:list", client = UserClientType.MANAGE)
    @GetMapping("/list")
    public TableDataInfo list(SysOperLog operLog) {
        startPage();
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    // @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:operlog:export", client = UserClientType.MANAGE)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysOperLog operLog) {
        List<SysOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SysOperLog> util = new ExcelUtil<SysOperLog>(SysOperLog.class);
        util.exportExcel(response, list, "操作日志");
    }

    // @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @PreAuthorize(hasPermi = "system:operlog:remove", client = UserClientType.MANAGE)
    @DeleteMapping("/{operIds}")
    public AjaxResult remove(@PathVariable Long[] operIds) {
        return toAjax(operLogService.deleteOperLogByIds(operIds));
    }

    @PreAuthorize(hasPermi = "system:operlog:remove", client = UserClientType.MANAGE)
    // @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return AjaxResult.success();
    }

    // @InnerAuth
    @PostMapping
    public AjaxResult add(@RequestBody SysOperLog operLog) {
        return toAjax(operLogService.insertOperlog(operLog));
    }
}
