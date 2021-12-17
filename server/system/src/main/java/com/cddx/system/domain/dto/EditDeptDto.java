package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 编辑部门传输类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "编辑部门传输类")
@EqualsAndHashCode(callSuper = true)
public class EditDeptDto extends com.cddx.domain.dto.AddDeptDto {

    @ApiModelProperty(value = "部门id", required = true)
    @NotNull(message = "部门id不能为空")
    private Long id;

}
