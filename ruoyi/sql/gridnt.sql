-- ----------------------------
-- Table structure for sys_role_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_org`;
CREATE TABLE `sys_role_org` (
    `role_id` bigint(20) NOT NULL COMMENT '角色ID',
    `organization_id` bigint(20) NOT NULL COMMENT '单位ID',
    PRIMARY KEY (`role_id`,`organization_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和单位关联表';
-- ----------------------------
-- Table structure for sa_organization
-- ----------------------------
DROP TABLE IF EXISTS `sa_organization`;
CREATE TABLE `sa_organization` (
    `organization_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '单位ID',
    `code` varchar(30) DEFAULT NULL COMMENT '单位编号',
    `name` varchar(30) NOT NULL COMMENT '单位名称',
    `parent_id` bigint(20) DEFAULT 0 COMMENT '父单位id',
    `ancestors` varchar(255) DEFAULT NULL COMMENT '祖级列表',
    `order_num` int(4) DEFAULT 0 COMMENT '显示顺序',
    `description` varchar(500) DEFAULT NULL COMMENT '单位简介',
    `manager` varchar(40) DEFAULT NULL COMMENT '单位负责人',
    `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
    `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
    `status` char(1) DEFAULT '0' COMMENT '单位状态（0正常 1停用）',
    `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
    `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`organization_id`) USING BTREE
) AUTO_INCREMENT=100 COMMENT ='单位维护表';

-- ----------------------------
-- Records of sa_organization
-- ----------------------------
BEGIN;
-- INSERT INTO `sa_organization`(`organization_id`, `code`, `name`, `parent_id`, `ancestors`, `order_num`, `description`, `manager`, `phone`, `email`, `status`, `del_flag`, `create_by`, `create_time`, `update_by`, `update_time`) VALUES (100, '001', '枣庄市国资委', 0, '0', 0, '枣庄市国资委', '枣庄市国资委', '17853633339', '17853633339@163.com', '0', '0', 'admin', '2021-05-17 11:06:24', 'admin', '2021-06-03 14:29:29');

INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1079, '单位管理', 1, 10, 'organization', 'system/organization/index', 1, 0, 'C', '0', '0', 'system:organization:list', 'build', 'admin', '2021-06-03 11:31:32', 'admin', '2021-06-03 11:32:30', '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1080, '单位查询', 1079, 1, '', NULL, 1, 0, 'F', '0', '0', 'system:organization:query', '#', 'admin', '2021-06-03 11:33:50', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1081, '单位新增', 1079, 2, '', NULL, 1, 0, 'F', '0', '0', 'system:organization:add', '#', 'admin', '2021-06-03 11:34:25', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1082, '单位修改', 1079, 3, '', NULL, 1, 0, 'F', '0', '0', 'system:organization:edit', '#', 'admin', '2021-06-03 11:35:04', '', NULL, '');
INSERT INTO `sys_menu`(`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) VALUES (1083, '单位删除', 1079, 4, '', NULL, 1, 0, 'F', '0', '0', 'system:organization:remove', '#', 'admin', '2021-06-03 11:35:34', '', NULL, '');

COMMIT;
