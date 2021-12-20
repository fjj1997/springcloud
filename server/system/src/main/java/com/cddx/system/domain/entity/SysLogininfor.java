package com.cddx.system.domain.entity;

import com.cddx.common.core.annotation.Excel;
import com.cddx.common.core.model.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 系统访问记录表 sys_logininfor
 *
 * @author 范劲松
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysLogininfor extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long infoId;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String userName;

    /**
     * 地址
     */
    @Excel(name = "地址")
    private String ipaddr;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "访问时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date accessTime;
}