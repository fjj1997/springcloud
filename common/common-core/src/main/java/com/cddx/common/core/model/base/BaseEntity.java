package com.cddx.common.core.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private Long createBy;

    /**
     * 创建时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long createTime;

    /**
     * 更新者
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateBy;

    /**
     * 更新时间
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateTime;

    /**
     * 删除标识
     */
    private boolean delFlag;

    /**
     * 备注
     */
    private String remark;

    /**
     * 请求参数
     */
    private Map<String, Object> params;

    public Map<String, Object> getParams() {
        if (params == null) {
            params = new HashMap<>();
        }
        return params;
    }
}
