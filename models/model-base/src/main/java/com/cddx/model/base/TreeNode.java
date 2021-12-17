package com.cddx.model.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 *
 * @author 范劲松
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TreeNode {

    @ApiModelProperty(value = "节点id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "父级节点id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    @SuppressWarnings("rawtypes")
    @ApiModelProperty(value = "树子节点")
    private List children = new ArrayList<>();
}
