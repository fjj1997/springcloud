package com.cddx.model.vo.menu;

import com.cddx.model.base.TreeNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 菜单
 *
 * @author 范劲松
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
public class MenuVo extends TreeNode {

    private String name;

}
