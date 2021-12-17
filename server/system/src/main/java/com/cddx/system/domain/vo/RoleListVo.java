package com.cddx.system.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 角色列表展示
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "角色列表展示")
public class RoleListVo {

    @ApiModelProperty(value = "角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "权限类型, 0 - 配置权限，1 - 全身权限")
    private char type;

    @ApiModelProperty(value = "人数")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long personCount;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;

}
