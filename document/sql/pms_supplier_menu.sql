/*
  练手模块 · 第 4 步：供应商菜单与接口权限
  在已有 mall 库上执行（Docker MySQL 示例）：
    docker cp document/sql/pms_supplier_menu.sql mall-mysql:/tmp/pms_supplier_menu.sql
    docker exec mall-mysql mysql -uroot -proot --default-character-set=utf8mb4 mall -e "source /tmp/pms_supplier_menu.sql"

  说明：
  - ums_menu.name 须与前端路由 name 一致：supplier / addSupplier / updateSupplier
  - ums_resource.url 对应后端 Controller：/supplier/**
  - admin 账号角色为 5，执行后需重新登录以刷新菜单与权限缓存
*/

SET NAMES utf8mb4;

-- 菜单（挂在「商品」parent_id=1 下）
INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`, `hidden`)
SELECT 26, 1, NOW(), '供应商管理', 1, 1, 'supplier', 'product-list', 0
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `ums_menu` WHERE `id` = 26 OR `name` = 'supplier');

-- 接口资源（商品模块 category_id=1）
INSERT INTO `ums_resource` (`id`, `create_time`, `name`, `url`, `description`, `category_id`)
SELECT 33, NOW(), '商品供应商管理', '/supplier/**', '练手模块-供应商', 1
FROM DUAL
WHERE NOT EXISTS (SELECT 1 FROM `ums_resource` WHERE `id` = 33 OR `url` = '/supplier/**');

-- 角色-菜单：超级管理员(5)、商品管理员(1)
INSERT INTO `ums_role_menu_relation` (`role_id`, `menu_id`)
SELECT 5, 26 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `ums_role_menu_relation` WHERE `role_id` = 5 AND `menu_id` = 26);
INSERT INTO `ums_role_menu_relation` (`role_id`, `menu_id`)
SELECT 1, 26 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `ums_role_menu_relation` WHERE `role_id` = 1 AND `menu_id` = 26);

-- 角色-资源
INSERT INTO `ums_role_resource_relation` (`role_id`, `resource_id`)
SELECT 5, 33 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `ums_role_resource_relation` WHERE `role_id` = 5 AND `resource_id` = 33);
INSERT INTO `ums_role_resource_relation` (`role_id`, `resource_id`)
SELECT 1, 33 FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM `ums_role_resource_relation` WHERE `role_id` = 1 AND `resource_id` = 33);
