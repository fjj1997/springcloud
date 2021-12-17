drop table if exists `sys_menu`;
create table sys_menu (
    id               bigint                   not null comment '菜单id'        primary key,
    name             varchar(16)              not null comment '菜单名',
    parent_id        bigint       default 0   null comment '父菜单ID',
    order_num        int(4)       default 0   null comment '显示顺序',
    path             varchar(200) default ''  null comment '路由地址',
    component        varchar(255)             null comment '组件路径',
    is_frame         int(1)       default 1   null comment '是否为外链（0是 1否）',
    is_cache         int(1)       default 0   null comment '是否缓存（0缓存 1不缓存）',
    menu_type        char         default ''  null comment '菜单类型（M目录 C菜单 F按钮）',
    menu_pom         char         default ''  null comment '菜单提供后台（M总后台）',
    visible          char         default '0' null comment '菜单状态（0显示 1隐藏）',
    status           char         default '0' null comment '菜单状态（0正常 1停用）',
    perms            varchar(100) default ''  null comment '权限标识',
    icon             varchar(100) default '#' null comment '菜单图标',
    create_id        bigint       default 0   null comment '创建人',
    create_time      bigint                   null comment '创建时间',
    last_update_id   bigint                   null comment '修改人',
    last_update_time bigint                   null comment '最新修改时间',
    del_flag         tinyint(1)   default 0   null comment '删除标识 0否 1是'
) comment '菜单表';

INSERT INTO `sys_menu`(`id`, `name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `menu_pom`, `visible`, `status`, `perms`, `icon`, `create_id`, `create_time`, `last_update_id`, `last_update_time`, `del_flag`) VALUES
(1, '工作台', 0, 1, 'index', 'index', 1, 0, 'C', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(7, '权限管理', 0, 7, 'authority', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(9, '操作日志', 0, 9, 'operlog', 'log/index', 1, 0, 'C', 'M', '0', '0', 'admin:operlog:list', '#', 1, 1638967309, NULL, NULL, 0),
-- 7 权限管理
(701, '后台用户管理', 7, 701, 'user', 'user/index', 1, 0, 'C', 'M', '0', '0', 'admin:user:list', '#', 1, 1638967309, NULL, NULL, 0),
(702, '角色管理', 7, 702, 'role', 'user/role', 1, 0, 'C', 'M', '0', '0', 'admin:role:list', '#', 1, 1638967309, NULL, NULL, 0),
(703, '部门管理', 7, 703, 'dept', 'user/dept', 1, 0, 'C', 'M', '0', '0', 'admin:dept:list', '#', 1, 1638967309, NULL, NULL, 0),
-- 701 后台用户管理
(70102, '查看用户详情', 701, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:user:detail', '#', 1, 1639054152, NULL, NULL, 0), 
(70103, '新增用户', 701, 0, 'authority/user/add', 'user/manageUser', 1, 0, 'C', 'M', '1', '0', 'admin:user:add', '#', 1, 1639054152, NULL, NULL, 0),
(70104, '编辑用户', 701, 0, 'authority/user/edit', 'user/manageUser', 1, 0, 'C', 'M', '1', '0', 'admin:user:edit', '#', 1, 1639054152, NULL, NULL, 0),
(70105, '删除用户', 701, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:user:delete', '#', 1, 1639054152, NULL, NULL, 0),
-- 702 角色管理
(70201, '查看角色详情', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:detail', '#', 1, 1638967309, NULL, NULL, 0), 
(70202, '新增角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:add', '#', 1, 1638967309, NULL, NULL, 0), 
(70203, '编辑角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:edit', '#', 1, 1638967309, NULL, NULL, 0), 
(70204, '删除角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:delete', '#', 1, 1638967309, NULL, NULL, 0), 
-- 703 部门管理
(70302, '查看部门详情', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:detail', '#', 1, 1639054152, NULL, NULL, 0), 
(70303, '新增部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:add', '#', 1, 1639054152, NULL, NULL, 0), 
(70304, '编辑部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:edit', '#', 1, 1639054152, NULL, NULL, 0), 
(70305, '删除部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:delete', '#', 1, 1639054152, NULL, NULL, 0),