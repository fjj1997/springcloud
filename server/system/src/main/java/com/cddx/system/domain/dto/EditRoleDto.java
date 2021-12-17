package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 编辑角色
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "编辑角色")
@EqualsAndHashCode(callSuper = true)
public class EditRoleDto extends AddRoleDto {

    @ApiModelProperty(value = "角色ID", required = true)
    @NotNull(message = "角色ID不能为空")
    private Long id;
}
