package com.cddx.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 部门拉框选择器
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "部门拉框选择器")
public class DeptSelectVo {

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long id;

    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String deptName;
}
