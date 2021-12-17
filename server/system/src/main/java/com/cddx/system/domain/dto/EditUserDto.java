package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * 编辑用户传输类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "编辑用户传输类")
@EqualsAndHashCode(callSuper = true)
public class EditUserDto extends AddUserDto {

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空")
    private Long id;

}
