-- 插入示例数据
INSERT INTO user (phone, nickname, password, create_time) VALUES
('13812345678', '张三', 'e10adc3949ba59abbe56e057f20f883e', NOW()),
('13987654321', '李四', 'e10adc3949ba59abbe56e057f20f883e', NOW())
ON DUPLICATE KEY UPDATE
    nickname = VALUES(nickname),
    password = VALUES(password);