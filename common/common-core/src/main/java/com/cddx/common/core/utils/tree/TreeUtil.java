package com.cddx.common.core.utils.tree;

import com.cddx.model.base.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形工具
 *
 * @author 范劲松
 */
public class TreeUtil {

    /**
     * 构建树形结构
     *
     * @param list 树节点列表
     * @param <T> 泛型
     * @return 结果
     */
    public static <T> List<T> buildTree(List<T> list) {
        return buildTree(list, 0L);
    }

    /**
     * 重写构造树形结构
     */
    public static <T> List<T> buildTree(List<T> list, Long parenId) {
        try {
            List<T> returnList = new ArrayList<>();
            for (T item : list) {
                TreeNode node = (TreeNode) item;
                if (node.getParentId().equals(parenId)) {
                    recursionFn(list, item);
                    returnList.add(item);
                }
            }
            return returnList;
        } catch (Exception ignore) {
            return list;
        }
    }

    /**
     * 递归列表
     */
    public static <T> void recursionFn(List<T> list, T t) {
        // 得到子节点列表
        List<T> children = getChildren(list, t);
        TreeNode node = (TreeNode) t;
        node.setChildren(children);
        for (T child : children) {
            if (hasChild(list, child)) {
                recursionFn(list, child);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getChildren(List<T> list, T t) {
        List<T> ts = new ArrayList<>();
        TreeNode tn = (TreeNode) t;
        // 将t现有的子节点先push进去
        if (tn.getChildren().size() > 0) {
            ts.addAll(tn.getChildren());
        }
        for (T item : list) {
            TreeNode node = (TreeNode) item;
            if (node.getParentId().equals(tn.getId())) {
                ts.add(item);
            }
        }
        return ts;
    }

    /**
     * 判断是否还有子节点
     */
    public static <T> boolean hasChild(List<T> list, T t) {
        return getChildren(list, t).size() > 0;
    }
}
