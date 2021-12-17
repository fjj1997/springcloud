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
(2, '调用情况', 0, 2, 'call_situation', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(3, '原子能力', 0, 3, 'power', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(4, '标准产品', 0, 4, 'product', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(5, '应用案例', 0, 5, 'case_study', 'useCase/index', 1, 0, 'C', 'M', '0', '0', 'admin:case_study:list', '#', 1, 1638967309, NULL, NULL, 0),
(6, '运营管理', 0, 6, 'operation', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(7, '权限管理', 0, 7, 'authority', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0),
(8, '服务商管理', 0, 8, 'service_provider', 'serviceManage/index', 1, 0, 'C', 'M', '0', '0', 'admin:case_study:list', '#', 1, 1638967309, NULL, NULL, 0), 
(9, '操作日志', 0, 9, 'operlog', 'log/index', 1, 0, 'C', 'M', '0', '0', 'admin:operlog:list', '#', 1, 1638967309, NULL, NULL, 0), 
-- 2 调用情况
(201, '调用总览', 2, 201, 'overview', 'call_situation/overview', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:overview', '#', 1, 1639054152, NULL, NULL, 0),
(202, '调用详情', 2, 202, 'detail', 'call_situation/detail', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:detail', '#', 1, 1639054152, NULL, NULL, 0),
(203, '数据大屏', 2, 203, 'screen', 'call_situation/screen', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:screen', '#', 1, 1639054152, NULL, NULL, 0),
-- 3 原子能力
(301, '原子能力管理', 3, 301, 'index', 'atomPowered/index', 1, 0, 'C', 'M', '0', '0', 'admin:power:list', '#', 1, 1638967309, NULL, NULL, 0),
(302, '原子能力分类管理', 3, 302, 'type', 'atomPowered/type', 1, 0, 'C', 'M', '0', '0', '', 'admin:power_type:list', 1, 1638967309, NULL, NULL, 0),
-- 4 标准产品
(401, '标准产品管理', 4, 401, 'index', 'product/index', 1, 0, 'C', 'M', '0', '0', 'admin:product:list', '#', 1, 1638967309, NULL, NULL, 0),
(402, '标准产品分类管理', 4, 402, 'type', 'product/type', 1, 0, 'C', 'M', '0', '0', '', 'admin:product_type:list', 1, 1638967309, NULL, NULL, 0),
-- 6 运营管理
(601, 'banner运营', 6, 601, 'banner', 'operation/banner', 1, 0, 'C', 'M', '0', '0', 'admin:operation_banner:list', '#', 1, 1638967309, NULL, NULL, 0),
(602, '原子能力运营', 6, 602, 'power', 'operation/power', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_power:list', 1, 1638967309, NULL, NULL, 0),
(603, '标准产品运营', 6, 603, 'product', 'operation/product', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_product:list', 1, 1638967309, NULL, NULL, 0),
(604, '应用案例运营', 6, 604, 'case_study', 'operation/case_study', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_case_study:list', 1, 1638967309, NULL, NULL, 0),
-- 7 权限管理
(701, '后台用户管理', 7, 701, 'user', 'user/index', 1, 0, 'C', 'M', '0', '0', 'admin:user:list', '#', 1, 1638967309, NULL, NULL, 0),
(702, '角色管理', 7, 702, 'role', 'user/role', 1, 0, 'C', 'M', '0', '0', 'admin:role:list', '#', 1, 1638967309, NULL, NULL, 0),
(703, '部门管理', 7, 703, 'dept', 'user/dept', 1, 0, 'C', 'M', '0', '0', 'admin:dept:list', '#', 1, 1638967309, NULL, NULL, 0),
-- 301 原子能力管理
(30101, '查看原子能力详情', 301, 30101, 'detail', 'atomPowered/detail', 1, 0, 'C', 'M', '1', '0', 'admin:power:detail', '#', 1, 1638967309, NULL, NULL, 0), 
(30102, '新增原子能力', 301, 30102, 'add', 'atomPowered/addPowered', 1, 0, 'C', 'M', '1', '0', 'admin:power:add', '#', 1, 1638967309, NULL, NULL, 0),
(30103, '编辑原子能力', 301, 30103, 'edit', 'atomPowered/edit', 1, 0, 'C', 'M', '1', '0', 'admin:power:edit', '#', 1, 1638967309, NULL, NULL, 0), 
(30104, '删除原子能力', 301, 30104, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power:delete', '#', 1, 1638967309, NULL, NULL, 0),
(30105, '下线/上线原子能力', 301, 30105, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power:online', '#', 1, 1638967309, NULL, NULL, 0),
-- 302 原子能力分类管理
(30201, '新增原子能力分类', 301, 30201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:add', '#', 1, 1638967309, NULL, NULL, 0),
(30202, '编辑原子能力分类', 301, 30202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:edit', '#', 1, 1638967309, NULL, NULL, 0),
(30203, '删除原子能力分类', 301, 30203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:delete', '#', 1, 1638967309, NULL, NULL, 0), 
-- 401 标准产品管理
(40101, '查看标准产品详情', 401, 40101, 'detail', 'product/detail', 1, 0, 'C', 'M', '1', '0', 'admin:product:detail', '#', 1, 1638967309, NULL, NULL, 0),
(40102, '新增标准产品', 401, 40102, 'add', 'product/add', 1, 0, 'C', 'M', '1', '0', 'admin:product:add', '#', 1, 1638967309, NULL, NULL, 0),
(40103, '编辑标准产品', 401, 40103, 'edit', 'product/edit', 1, 0, 'C', 'M', '1', '0', 'admin:product:edit', '#', 1, 1638967309, NULL, NULL, 0),
(40104, '删除标准产品', 401, 40104, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:product:delete', '#', 1, 1638967309, NULL, NULL, 0),
(40105, '下线/上线标准产品', 401, 40105, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:product:online', '#', 1, 1638967309, NULL, NULL, 0),
-- 402 标准产品分类管理
(40201, '新增标准产品分类', 401, 40201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:add', '#', 1, 1638967309, NULL, NULL, 0),
(40202, '编辑标准产品分类', 401, 40202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:edit', '#', 1, 1638967309, NULL, NULL, 0),
(40203, '删除标准产品分类', 401, 40203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:delete', '#', 1, 1638967309, NULL, NULL, 0),
-- 500 应用案例
(50001, '查看应用案例详情', 5, 50001, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:detail', '#', 1, 1639054152, NULL, NULL, 0),
(50002, '新增应用案例', 5, 50002, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:add', '#', 1, 1639054152, NULL, NULL, 0),
(50003, '编辑应用案例', 5, 50003, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:edit', '#', 1, 1639054152, NULL, NULL, 0),
(50004, '删除应用案例', 5, 50004, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:delete', '#', 1, 1639054152, NULL, NULL, 0),
-- 601 banner运营
(60101, '新增banner', 601, 60101, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:add', '#', 1, 1639054152, NULL, NULL, 0),
(60102, '删除banner', 601, 60102, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:delete', '#', 1, 1639054152, NULL, NULL, 0),
(60103, '查看banner详情', 601, 60103, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:detail', '#', 1, 1639054152, NULL, NULL, 0),
-- 602 原子能力运营
(60201, '新增原子能力推荐', 602, 60201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:add', '#', 1, 1639054152, NULL, NULL, 0),
(60202, '删除原子能力推荐', 602, 60202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:delete', '#', 1, 1639054152, NULL, NULL, 0),
(60203, '查看原子能力推荐详情', 602, 60203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:detail', '#', 1, 1639054152, NULL, NULL, 0),
-- 603 标准产品运营
(60301, '新增标准产品推荐', 603, 60301, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:add', '#', 1, 1639054152, NULL, NULL, 0), 
(60302, '删除标准产品推荐', 603, 60302, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:delete', '#', 1, 1639054152, NULL, NULL, 0), 
(60303, '查看标准产品推荐详情', 603, 60303, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:detail', '#', 1, 1639054152, NULL, NULL, 0), 
-- 604 应用案例运营
(60401, '新增应用案例推荐', 604, 60401, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:add', '#', 1, 1639054152, NULL, NULL, 0), 
(60402, '删除应用案例推荐', 604, 60402, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:delete', '#', 1, 1639054152, NULL, NULL, 0), 
(60403, '查看应用案例推荐详情', 604, 60403, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:detail', '#', 1, 1639054152, NULL, NULL, 0), 
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
-- 800 服务商管理
(80001, '查看服务商详情', 8, 80001, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:service_provider:detail', '#', 1, 1639054152, NULL, NULL, 0),
(80002, '新增服务商', 8, 80002, 'service_provider/add', 'serviceManage/manageService', 1, 0, 'C', 'M', '1', '0', 'admin:service_provider:add', '#', 1, 1639054152, NULL, NULL, 0),
(80003, '编辑服务商信息', 8, 80003, 'service_provider/edit', 'serviceManage/manageService', 1, 0, 'C', 'M', '1', '0', 'admin:service_provider:edit', '#', 1, 1639054152, NULL, NULL, 0),
(80004, '删除服务商', 8, 80004, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:service_provider:delete', '#', 1, 1639054152, NULL, NULL, 0)