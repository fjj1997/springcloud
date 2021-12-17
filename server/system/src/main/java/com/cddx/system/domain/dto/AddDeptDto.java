package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * 添加部门
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "部门列表查询传输类")
public class AddDeptDto {

    @NotBlank(message = "部门名不能为空")
    @ApiModelProperty(value = "部门名")
    private String name;

}
