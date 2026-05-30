-- prod 配置使用 reader/123456，Docker 启动时自动创建
CREATE USER IF NOT EXISTS 'reader'@'%' IDENTIFIED BY '123456';
GRANT ALL PRIVILEGES ON mall.* TO 'reader'@'%';
FLUSH PRIVILEGES;
