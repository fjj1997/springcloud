package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 部门列表查询传输类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "部门列表查询传输类")
public class ListDeptDto {

    @ApiModelProperty(value = "部门名")
    private String name;

}
