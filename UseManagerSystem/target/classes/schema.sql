-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID，主键',
    phone VARCHAR(20) NOT NULL UNIQUE COMMENT '手机号，用户的唯一标识',
    nickname VARCHAR(50) NOT NULL COMMENT '昵称，用户显示名称',
    password VARCHAR(100) NOT NULL COMMENT '密码，加密后的密码',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间，用户创建的时间戳',
    INDEX idx_phone (phone) COMMENT '手机号索引'
) COMMENT='用户表';

-- 插入示例数据
INSERT INTO user (phone, nickname, password, create_time) VALUES
('13812345678', '张三', 'e10adc3949ba59abbe56e057f20f883e', NOW()),
('13987654321', '李四', 'e10adc3949ba59abbe56e057f20f883e', NOW());

-- 验证表创建
SELECT 'User table created successfully' AS message;