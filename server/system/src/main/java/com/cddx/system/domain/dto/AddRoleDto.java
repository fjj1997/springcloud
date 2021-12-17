package com.cddx.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建角色传输类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "创建角色传输类")
public class AddRoleDto {

    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "权限配置类型")
    private char type;

    @ApiModelProperty(value = "权限菜单id")
    private List<Long> menuIds = new ArrayList<>();
}
