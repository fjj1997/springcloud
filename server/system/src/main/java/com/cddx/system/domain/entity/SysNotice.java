package com.cddx.system.domain.entity;

import com.cddx.common.core.xss.Xss;
import com.cddx.common.core.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 *
 * @author 范劲松
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class SysNotice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 公告ID
     */
    private Long noticeId;

    /**
     * 公告标题
     */
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;
}
