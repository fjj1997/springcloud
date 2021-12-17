package com.cddx.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户与部门关联
 *
 * @author 范劲松
 */
@Data
@ToString
@Accessors(chain = true)
@TableName(value = "sys_user_dept", autoResultMap = true)
public class SysUserDept {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 岗位
     */
    private String position;

}
