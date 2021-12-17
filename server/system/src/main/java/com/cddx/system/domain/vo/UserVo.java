package com.cddx.system.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户列表展示类
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "用户信息展示")
public class UserVo {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String telephone;

    /**
     * 账号状态
     */
    @ApiModelProperty(value = "账号状态")
    private char status;

    /**
     * 角色id
     */
    @ApiModelProperty(value = "角色id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private String roleName;

    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;

    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String deptName;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createName;

}
