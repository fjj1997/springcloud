package com.cddx.system.controller;

import com.cddx.common.core.constant.UserConstants;
import com.cddx.common.core.enums.UserClientType;
import com.cddx.common.core.model.entity.SysDictType;
import com.cddx.common.core.utils.poi.ExcelUtil;
import com.cddx.common.core.web.controller.BaseController;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.response.AjaxResult;
import com.cddx.common.security.annotation.PreAuthorize;
import com.cddx.common.security.utils.SecurityUtils;
import com.cddx.system.service.SysDictTypeService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 数据字典信息
 *
 * @author 范劲松
 */
@RestController
@RequestMapping("/dict/type")
public class SysDictTypeController extends BaseController {
    @Resource
    private SysDictTypeService dictTypeService;

    @PreAuthorize(hasPermi = "system:dict:list", client = UserClientType.MANAGE)
    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    // @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @PreAuthorize(hasPermi = "system:dict:export", client = UserClientType.MANAGE)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SysDictType> util = new ExcelUtil<SysDictType>(SysDictType.class);
        util.exportExcel(response, list, "字典类型");
    }

    /**
     * 查询字典类型详细
     */
    @PreAuthorize(hasPermi = "system:dict:query", client = UserClientType.MANAGE)
    @GetMapping(value = "/{dictId}")
    public AjaxResult getInfo(@PathVariable Long dictId) {
        return AjaxResult.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:add", client = UserClientType.MANAGE)
    // @Log(title = "字典类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return AjaxResult.error("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(SecurityUtils.getUserId());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:edit", client = UserClientType.MANAGE)
    // @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysDictType dict) {
        if (UserConstants.NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dict))) {
            return AjaxResult.error("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(SecurityUtils.getUserId());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @PreAuthorize(hasPermi = "system:dict:remove", client = UserClientType.MANAGE)
    // @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dictIds}")
    public AjaxResult remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return success();
    }

    /**
     * 刷新字典缓存
     */
    @PreAuthorize(hasPermi = "system:dict:remove", client = UserClientType.MANAGE)
    // @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache() {
        dictTypeService.resetDictCache();
        return AjaxResult.success();
    }

    /**
     * 获取字典选择框列表
     */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return AjaxResult.success(dictTypes);
    }
}
