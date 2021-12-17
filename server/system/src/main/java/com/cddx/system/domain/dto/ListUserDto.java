package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户列表查询传输类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "用户列表查询传输类")
public class ListUserDto {

    /**
     * 姓名筛选
     */
    @ApiModelProperty(value = "姓名")
    private String username;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Long deptId;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long roleId;

}
