package com.cddx.common.core.utils.menu;

import com.cddx.common.core.constant.Constants;
import com.cddx.common.core.enums.MenuType;
import com.cddx.common.core.utils.StringUtils;
import com.cddx.model.entity.SysMenu;
import com.cddx.model.vo.menu.MetaVo;
import com.cddx.model.vo.menu.RouterVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单工具
 *
 * @author 范劲松
 */
public class MenuUtils {

    /** 是否菜单外链（是） */
    public static final Integer YES_FRAME = 0;

    /** 是否菜单外链（否） */
    public static final Integer NO_FRAME = 1;

    /** Layout组件标识 */
    public final static String LAYOUT = "Layout";

    /** ParentView组件标识 */
    public final static String PARENT_VIEW = "ParentView";

    /** InnerLink组件标识 */
    public final static String INNER_LINK = "InnerLink";

    public static List<RouterVo> buildMenus(List<SysMenu> list) {
        return list.stream().map(menu -> {
            RouterVo router = new RouterVo();
            router.setHidden('1' == menu.getVisible());
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
            if (MenuType.DIR.eq(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
            } else if (isMenuFrame(menu)) {
                router.setMeta(null);
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), 1 == menu.getIsCache(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            } else if (menu.getParentId().intValue() == 0 && isInnerLink(menu)) {
                router.setMeta(new MetaVo(menu.getName(), menu.getIcon()));
                router.setPath("/inner");
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaVo(menu.getName(), menu.getIcon(), menu.getPath()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            return router;
        }).collect(Collectors.toList());
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public static String getRouteName(SysMenu menu) {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame(menu)) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public static String getRouterPath(SysMenu menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式
        if (menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            routerPath = innerLinkReplaceEach(routerPath);
        }
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && MenuType.DIR.eq(menu.getMenuType())
                && NO_FRAME.equals(menu.getIsFrame())) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMenuFrame(menu)) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public static String getComponent(SysMenu menu) {
        String component = LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
            component = menu.getComponent();
        } else if (StringUtils.isEmpty(menu.getComponent()) && menu.getParentId().intValue() != 0 && isInnerLink(menu)) {
            component = INNER_LINK;
        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
            component = PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public static boolean isMenuFrame(SysMenu menu) {
        return menu.getParentId().intValue() == 0 && MenuType.MENU.eq(menu.getMenuType())
                && menu.getIsFrame().equals(NO_FRAME);
    }

    /**
     * 是否为内链组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public static boolean isInnerLink(SysMenu menu) {
        return menu.getIsFrame().equals(NO_FRAME) && StringUtils.ishttp(menu.getPath());
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public static boolean isParentView(SysMenu menu) {
        return menu.getParentId().intValue() != 0 && MenuType.DIR.eq(menu.getMenuType());
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return
     */
    public static String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS},
                new String[]{"", ""});
    }
}
