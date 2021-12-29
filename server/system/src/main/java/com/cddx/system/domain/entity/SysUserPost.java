package com.cddx.system.domain.entity;

import lombok.Data;
import lombok.ToString;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author 范劲松
 */
@Data
@ToString
public class SysUserPost {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}
