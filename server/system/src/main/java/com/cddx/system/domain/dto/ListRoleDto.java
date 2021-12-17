package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 角色列表筛选条件
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "角色列表筛选条件")
public class ListRoleDto {

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "权限类别")
    private char type;

}
