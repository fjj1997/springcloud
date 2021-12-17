package com.cddx.common.core.utils.menu;

import com.cddx.model.entity.SysMenu;
import com.cddx.model.vo.menu.RouterVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单工具
 *
 * @author 范劲松
 */
public class MenuUtils {

    public static List<RouterVo> buildMenus(List<SysMenu> list) {
        return list.stream().map(item -> {
            return new RouterVo();
        }).collect(Collectors.toList());
    }

}
