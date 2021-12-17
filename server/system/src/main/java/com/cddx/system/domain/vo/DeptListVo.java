package com.cddx.system.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 部门列表展示
 *
 * @author 范劲松
 */
@Data
@ToString
@ApiModel(value = "部门列表展示")
public class DeptListVo {

    @ApiModelProperty(value = "部门id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "部门名")
    private String name;

    @ApiModelProperty(value = "部门人数")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long personCount;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;

}
