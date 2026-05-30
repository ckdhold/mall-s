/*
  模块：商品供应商管理（练手模块 · 第 1 步）
  表名：pms_supplier
  库名：mall

  执行方式（Docker 已启动时，必须带 utf8mb4，否则终端可能显示 ????）：
    docker cp document/sql/pms_supplier.sql mall-mysql:/tmp/pms_supplier.sql
    docker exec mall-mysql mysql -uroot -proot --default-character-set=utf8mb4 mall -e "source /tmp/pms_supplier.sql"

  验证查询（同样要带 utf8mb4）：
    docker exec mall-mysql mysql -uroot -proot --default-character-set=utf8mb4 mall -e "SELECT id,name,code,status FROM pms_supplier;"

  或在 Navicat / DBeaver 连接 localhost:3307，库 mall，字符集 utf8mb4，执行本脚本。
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pms_supplier
-- ----------------------------
DROP TABLE IF EXISTS `pms_supplier`;
CREATE TABLE `pms_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '供应商名称',
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '供应商编码',
  `contact_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(11) NULL DEFAULT 0 COMMENT '排序（越大越靠前）',
  `status` int(1) NULL DEFAULT 1 COMMENT '状态：0->禁用；1->启用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_supplier_code` (`code`) USING BTREE,
  KEY `idx_supplier_name` (`name`) USING BTREE,
  KEY `idx_supplier_status` (`status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '商品供应商表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pms_supplier（演示数据）
-- ----------------------------
INSERT INTO `pms_supplier` (`name`, `code`, `contact_name`, `contact_phone`, `email`, `address`, `remark`, `sort`, `status`, `create_time`) VALUES
('深圳宏联供应链有限公司', 'SUP001', '张经理', '13800138001', 'zhang@honglian.com', '广东省深圳市南山区科技园', '主采数码类', 100, 1, NOW()),
('杭州云仓百货供应商', 'SUP002', '李女士', '13800138002', 'li@yuncang.com', '浙江省杭州市余杭区', '日用品补货', 90, 1, NOW()),
('广州优品服饰厂', 'SUP003', '王总', '13800138003', 'wang@youpin.com', '广东省广州市白云区', '服装类', 80, 1, NOW()),
('上海冷链食材供应', 'SUP004', '赵主管', '13800138004', NULL, '上海市浦东新区', '暂停合作示例', 10, 0, NOW());

SET FOREIGN_KEY_CHECKS = 1;
