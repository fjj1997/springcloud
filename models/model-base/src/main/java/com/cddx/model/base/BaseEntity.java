package com.cddx.model.base;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * Entity基类
 *
 * @author 范劲松
 */
@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private char status;

    /**
     * 创建者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createId;

    /**
     * 创建时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lastUpdateId;

    /**
     * 更新时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long lastUpdateTime;

    /**
     * 删除标识
     */
    private boolean delFlag;

    /**
     * 备注
     */
    private String remark;
}
