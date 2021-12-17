package com.cddx.common.core.web.service;

import com.cddx.common.core.enums.ResultEnum;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.common.core.utils.sql.SqlUtil;
import com.cddx.common.core.web.page.PageDomain;
import com.cddx.common.core.web.page.TableDataInfo;
import com.cddx.common.core.web.page.TableSupport;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @author 范劲松
 */
@Log4j2
public class BaseService {

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            Boolean reasonable = pageDomain.getReasonable();
            PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(ResultEnum.SUCCESS_CODE.getCode());
        rspData.setRows(list);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> source, List<?> target) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(ResultEnum.SUCCESS_CODE.getCode());
        rspData.setRows(target);
        rspData.setMsg("查询成功");
        rspData.setTotal(new PageInfo(source).getTotal());
        return rspData;
    }

}
