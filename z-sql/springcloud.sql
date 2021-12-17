-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL COMMENT '部门id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门名',
  `create_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `last_update_time` bigint(20) NULL DEFAULT NULL COMMENT '最新修改时间',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, '产创中心', 1468072207891660801, 1638877893, NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (2, 'IT支撑中心', 1468072207891660801, 1638877893, NULL, NULL, 0);
INSERT INTO `sys_dept` VALUES (1468557581076553729, '测试部门', 1468190467955154579, 1638966386, 1468190467955154579, 1638966640, 1);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL COMMENT '菜单id',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(4) NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int(1) NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `menu_pom` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单提供后台（M总后台）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '#' COMMENT '菜单图标',
  `create_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `last_update_time` bigint(20) NULL DEFAULT NULL COMMENT '最新修改时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '工作台', 0, 1, 'index', 'index', 1, 0, 'C', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (2, '调用情况', 0, 2, 'call_situation', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (3, '原子能力', 0, 3, 'power', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (4, '标准产品', 0, 4, 'product', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (5, '应用案例', 0, 5, 'case_study', 'useCase/index', 1, 0, 'C', 'M', '0', '0', 'admin:case_study:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (6, '运营管理', 0, 6, 'operation', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (7, '权限管理', 0, 7, 'authority', NULL, 1, 0, 'M', 'M', '0', '0', '', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (8, '服务商管理', 0, 8, 'service_provider', 'serviceManage/index', 1, 0, 'C', 'M', '0', '0', 'admin:case_study:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (9, '操作日志', 0, 9, 'operlog', 'log/index', 1, 0, 'C', 'M', '0', '0', 'admin:operlog:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (201, '调用总览', 2, 201, 'overview', 'call_situation/overview', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:overview', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (202, '调用详情', 2, 202, 'detail', 'call_situation/detail', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (203, '数据大屏', 2, 203, 'screen', 'call_situation/screen', 1, 0, 'C', 'M', '0', '0', 'admin:call_situation:screen', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (301, '原子能力管理', 3, 301, 'index', 'atomPowered/index', 1, 0, 'C', 'M', '0', '0', 'admin:power:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (302, '原子能力分类管理', 3, 302, 'type', 'atomPowered/type', 1, 0, 'C', 'M', '0', '0', '', 'admin:power_type:list', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (401, '标准产品管理', 4, 401, 'index', 'product/index', 1, 0, 'C', 'M', '0', '0', 'admin:product:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (402, '标准产品分类管理', 4, 402, 'type', 'product/type', 1, 0, 'C', 'M', '0', '0', '', 'admin:product_type:list', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (601, 'banner运营', 6, 601, 'banner', 'operation/banner', 1, 0, 'C', 'M', '0', '0', 'admin:operation_banner:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (602, '原子能力运营', 6, 602, 'power', 'operation/power', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_power:list', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (603, '标准产品运营', 6, 603, 'product', 'operation/product', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_product:list', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (604, '应用案例运营', 6, 604, 'case_study', 'operation/case_study', 1, 0, 'C', 'M', '0', '0', '', 'admin:operation_case_study:list', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (701, '后台用户管理', 7, 701, 'user', 'user/index', 1, 0, 'C', 'M', '0', '0', 'admin:user:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (702, '角色管理', 7, 702, 'role', 'user/role', 1, 0, 'C', 'M', '0', '0', 'admin:role:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (703, '部门管理', 7, 703, 'dept', 'user/dept', 1, 0, 'C', 'M', '0', '0', 'admin:dept:list', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30101, '查看原子能力详情', 301, 30101, 'detail', 'atomPowered/detail', 1, 0, 'C', 'M', '1', '0', 'admin:power:detail', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30102, '新增原子能力', 301, 30102, 'add', 'atomPowered/addPowered', 1, 0, 'C', 'M', '1', '0', 'admin:power:add', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30103, '编辑原子能力', 301, 30103, 'edit', 'atomPowered/edit', 1, 0, 'C', 'M', '1', '0', 'admin:power:edit', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30104, '删除原子能力', 301, 30104, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power:delete', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30105, '下线/上线原子能力', 301, 30105, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power:online', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30201, '新增原子能力分类', 301, 30201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:add', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30202, '编辑原子能力分类', 301, 30202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:edit', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (30203, '删除原子能力分类', 301, 30203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:power_type:delete', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40101, '查看标准产品详情', 401, 40101, 'detail', 'product/detail', 1, 0, 'C', 'M', '1', '0', 'admin:product:detail', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40102, '新增标准产品', 401, 40102, 'add', 'product/add', 1, 0, 'C', 'M', '1', '0', 'admin:product:add', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40103, '编辑标准产品', 401, 40103, 'edit', 'product/edit', 1, 0, 'C', 'M', '1', '0', 'admin:product:edit', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40104, '删除标准产品', 401, 40104, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:product:delete', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40105, '下线/上线标准产品', 401, 40105, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:product:online', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40201, '新增标准产品分类', 401, 40201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:add', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40202, '编辑标准产品分类', 401, 40202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:edit', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (40203, '删除标准产品分类', 401, 40203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:product_type:delete', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (50001, '查看应用案例详情', 5, 50001, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (50002, '新增应用案例', 5, 50002, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (50003, '编辑应用案例', 5, 50003, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:edit', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (50004, '删除应用案例', 5, 50004, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:case_study:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60101, '新增banner', 601, 60101, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60102, '删除banner', 601, 60102, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60103, '查看banner详情', 601, 60103, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_banner:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60201, '新增原子能力推荐', 602, 60201, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60202, '删除原子能力推荐', 602, 60202, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60203, '查看原子能力推荐详情', 602, 60203, '', NULL, 1, 0, 'F', 'M', '1', '0', 'admin:operation_power:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60301, '新增标准产品推荐', 603, 60301, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60302, '删除标准产品推荐', 603, 60302, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60303, '查看标准产品推荐详情', 603, 60303, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_product:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60401, '新增应用案例推荐', 604, 60401, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60402, '删除应用案例推荐', 604, 60402, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (60403, '查看应用案例推荐详情', 604, 60403, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:operation_case_study:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70102, '查看用户详情', 701, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:user:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70103, '新增用户', 701, 0, 'authority/user/add', 'user/manageUser', 1, 0, 'C', 'M', '1', '0', 'admin:user:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70104, '编辑用户', 701, 0, 'authority/user/edit', 'user/manageUser', 1, 0, 'C', 'M', '1', '0', 'admin:user:edit', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70105, '删除用户', 701, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:user:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70201, '查看角色详情', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:detail', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70202, '新增角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:add', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70203, '编辑角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:edit', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70204, '删除角色', 702, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:role:delete', '#', 1, 1638967309, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70302, '查看部门详情', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70303, '新增部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70304, '编辑部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:edit', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (70305, '删除部门', 703, 0, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:dept:delete', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (80001, '查看服务商详情', 8, 80001, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:service_provider:detail', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (80002, '新增服务商', 8, 80002, 'service_provider/add', 'serviceManage/manageService', 1, 0, 'C', 'M', '1', '0', 'admin:service_provider:add', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (80003, '编辑服务商信息', 8, 80003, 'service_provider/edit', 'serviceManage/manageService', 1, 0, 'C', 'M', '1', '0', 'admin:service_provider:edit', '#', 1, 1639054152, NULL, NULL, 0);
INSERT INTO `sys_menu` VALUES (80004, '删除服务商', 8, 80004, '', NULL, 1, 0, 'F', 'M', '0', '0', 'admin:service_provider:delete', '#', 1, 1639054152, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL COMMENT '角色id',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_sort` int(4) NOT NULL COMMENT '显示顺序',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '权限类别（0 配置权限，1全部权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `create_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `last_update_time` bigint(20) NULL DEFAULT NULL COMMENT '最新修改时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '删除标识 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 0, '1', '0', 1, 1638877893, NULL, NULL, 0);
INSERT INTO `sys_role` VALUES (2, '平台运营管理', 1, '1', '0', 1, 1638877893, 1468190467955154579, 1638965824, 0);
INSERT INTO `sys_role` VALUES (3, '信息维护', 2, '0', '0', 1, 1638877893, 1468186412829433857, 1639049250, 0);
INSERT INTO `sys_role` VALUES (1468552316134551553, '测试', 0, '1', '0', 1468190467955154579, 1638965131, NULL, NULL, 1);
INSERT INTO `sys_role` VALUES (1468556567640432641, '测试', 0, '0', '0', 1468190467955154579, 1638966144, NULL, NULL, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (3, 5);
INSERT INTO `sys_role_menu` VALUES (3, 6);
INSERT INTO `sys_role_menu` VALUES (3, 7);
INSERT INTO `sys_role_menu` VALUES (3, 8);
INSERT INTO `sys_role_menu` VALUES (3, 9);
INSERT INTO `sys_role_menu` VALUES (3, 201);
INSERT INTO `sys_role_menu` VALUES (3, 202);
INSERT INTO `sys_role_menu` VALUES (3, 203);
INSERT INTO `sys_role_menu` VALUES (3, 701);
INSERT INTO `sys_role_menu` VALUES (3, 702);
INSERT INTO `sys_role_menu` VALUES (3, 703);
INSERT INTO `sys_role_menu` VALUES (3, 70201);
INSERT INTO `sys_role_menu` VALUES (3, 7002);
INSERT INTO `sys_role_menu` VALUES (3, 70103);
INSERT INTO `sys_role_menu` VALUES (3, 70101);
INSERT INTO `sys_role_menu` VALUES (3, 70202);
INSERT INTO `sys_role_menu` VALUES (3, 70203);
INSERT INTO `sys_role_menu` VALUES (3, 70204);
INSERT INTO `sys_role_menu` VALUES (3, 70205);
INSERT INTO `sys_role_menu` VALUES (3, 70301);
INSERT INTO `sys_role_menu` VALUES (3, 70302);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `telephone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户手机号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '用户密码',
  `login_ip` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后一次登录ip',
  `login_time` bigint(10) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `create_id` bigint(20) NULL DEFAULT 0 COMMENT '创建人',
  `create_time` bigint(20) NULL DEFAULT NULL COMMENT '创建时间',
  `last_update_id` bigint(20) NULL DEFAULT NULL COMMENT '修改人',
  `last_update_time` bigint(20) NULL DEFAULT NULL COMMENT '最新修改时间',
  `del_flag` tinyint(1) NULL DEFAULT NULL COMMENT '删除标识 0否 1是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '管理员（系统）用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, '张三', '18981941327', NULL, NULL, NULL, '0', 1, 1638877000, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (1468186412829433857, '黄总', '17360150825', NULL, NULL, NULL, '0', 1, 1638877893, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (1468190387704274945, 'xiaofan', '18282640650', NULL, NULL, NULL, '0', 1, 1638878840, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (1468190467955154579, '小feng', '15983095930', '$2a$10$MVtlRbhTcGvN9GjIzNSeDu1FpqKQV2EEA3Y5lI1TXRtSHF5J3bWRG', NULL, NULL, '0', 1, 1638878859, 1468190467955154579, 1639105881144, 0);
INSERT INTO `sys_user` VALUES (1468190467987447809, 'dalao', '17760420247', NULL, NULL, NULL, '0', 1, 1638878859, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (1468398316831526914, 'dalao', '17760420248', '', NULL, NULL, '0', 1468190467955154579, 1638928414, NULL, NULL, 0);
INSERT INTO `sys_user` VALUES (1469193526637424642, '尚laoban', '15892592267', '', NULL, NULL, '0', 1468186412829433857, 1639118007, NULL, NULL, 0);

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `dept_id` bigint(20) NOT NULL COMMENT '部门id',
  `position` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职位'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES (1468186412829433857, 1, '开发');
INSERT INTO `sys_user_dept` VALUES (1468190387704274945, 1, '开发');
INSERT INTO `sys_user_dept` VALUES (1468190467987447809, 1, '开发');
INSERT INTO `sys_user_dept` VALUES (1468190467955154579, 1, '开发');
INSERT INTO `sys_user_dept` VALUES (1468398316831526914, 1, '开发');
INSERT INTO `sys_user_dept` VALUES (1469193526637424642, 1, '老板');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1468186412829433857, 1);
INSERT INTO `sys_user_role` VALUES (1468190387704274945, 1);
INSERT INTO `sys_user_role` VALUES (1468190467987447809, 1);
INSERT INTO `sys_user_role` VALUES (1468190467955154579, 1);
INSERT INTO `sys_user_role` VALUES (1468398316831526914, 1);
INSERT INTO `sys_user_role` VALUES (1468186412829433900, 3);
INSERT INTO `sys_user_role` VALUES (1469193526637424642, 1);