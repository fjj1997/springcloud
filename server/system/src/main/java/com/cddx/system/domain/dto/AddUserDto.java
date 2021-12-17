package com.cddx.system.domain.dto;

import com.cddx.constants.RegexpConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 增加User传输层
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "增加User传输层")
public class AddUserDto {

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank(message = "姓名不能为空")
    private String username;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号", required = true)
    @Pattern(regexp = RegexpConstants.PHONE, message = "手机号不正确")
    private String telephone;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id", required = true)
    private Long deptId;

    /**
     * 职位
     */
    @ApiModelProperty(value = "职位", required = true)
    private String position;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id", required = true)
    private Long roleId;

}
