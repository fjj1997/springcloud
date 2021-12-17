package com.cddx.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 角色下拉框选择
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "角色下拉框选择器")
public class RoleSelectVo {

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    private Long id;

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String roleName;

}
