package com.cddx.system.controller;

import com.cddx.common.core.enums.UserClientType;
import com.cddx.common.core.web.controller.BaseController;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.log.annotation.Log;
import com.cddx.common.log.enums.BusinessType;
import com.cddx.common.security.annotation.PreAuthorize;
import com.cddx.common.security.utils.SecurityUtils;
import com.cddx.system.domain.entity.SysNotice;
import com.cddx.system.service.SysNoticeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告 信息操作处理
 *
 * @author 范劲松
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController {
    @Resource
    private SysNoticeService noticeService;

    /**
     * 获取通知公告列表
     */
    @PreAuthorize(hasPermi = "system:notice:list", client = UserClientType.MANAGE)
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @PreAuthorize(hasPermi = "system:notice:query", client = UserClientType.MANAGE)
    @GetMapping(value = "/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    /**
     * 新增通知公告
     */
    @PreAuthorize(hasPermi = "system:notice:add", client = UserClientType.MANAGE)
    @Log(title = "通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysNotice notice) {
        notice.setCreateBy(SecurityUtils.getUserId());
        return toAjax(noticeService.insertNotice(notice));
    }

    /**
     * 修改通知公告
     */
    @PreAuthorize(hasPermi = "system:notice:edit", client = UserClientType.MANAGE)
    @Log(title = "通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysNotice notice) {
        notice.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(noticeService.updateNotice(notice));
    }

    /**
     * 删除通知公告
     */
    @PreAuthorize(hasPermi = "system:notice:remove", client = UserClientType.MANAGE)
    @Log(title = "通知公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{noticeIds}")
    public AjaxResult remove(@PathVariable Long[] noticeIds) {
        return toAjax(noticeService.deleteNoticeByIds(noticeIds));
    }
}
