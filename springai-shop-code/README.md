# Spring AI 商城系统

一个基于 Spring AI、Vue 3 和 Ant Design Vue 的现代化商城系统。

## 项目结构

```
springai-shop-code/
├── app-backend/          # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/shop/
│   │   │   │   ├── controller/    # 控制器层
│   │   │   │   ├── service/       # 服务层
│   │   │   │   ├── mapper/        # 数据访问层
│   │   │   │   ├── entity/        # 实体类
│   │   │   │   ├── dto/           # 数据传输对象
│   │   │   │   ├── config/        # 配置类
│   │   │   │   ├── common/        # 公共类
│   │   │   │   ├── exception/     # 异常处理
│   │   │   │   └── util/          # 工具类
│   │   │   └── resources/
│   │   │       ├── sql/           # 数据库脚本
│   │   │       └── application.yml
│   │   └── pom.xml
│
└── app-frontend/         # 前端项目
    ├── src/
    │   ├── api/          # API接口
    │   ├── components/   # 组件
    │   ├── router/       # 路由
    │   ├── stores/       # 状态管理
    │   ├── utils/        # 工具函数
    │   ├── views/        # 页面
    │   ├── App.vue
    │   ├── main.js
    │   └── style.css
    ├── index.html
    ├── package.json
    └── vite.config.js
```

## 技术栈

### 后端
- Java 17
- Spring Boot 3.2.0
- Spring AI (OpenAI集成)
- MyBatis Plus
- MySQL 8.0
- Redis
- JWT (身份认证)
- Hutool (工具类库)

### 前端
- Vue 3
- Vite 5
- Ant Design Vue 4
- Vue Router 4
- Pinia (状态管理)
- Axios (HTTP客户端)

## 功能特性

### 核心功能
- ✅ 用户注册/登录
- ✅ 商品浏览和搜索
- ✅ 商品详情展示
- ✅ 购物车管理
- ✅ 订单管理
- ✅ 响应式设计

### AI功能 (Spring AI)
- ✅ 智能客服对话
- ✅ 商品智能推荐
- ✅ 商品描述优化

## 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 后端启动

1. **初始化数据库**
```bash
# 执行SQL脚本
mysql -u root -p < app-backend/src/main/resources/sql/init.sql
```

2. **修改配置**
编辑 `app-backend/src/main/resources/application.yml`:
- 修改数据库连接信息
- 配置Redis连接
- 设置OpenAI API Key (可选,用于AI功能)

3. **启动后端**
```bash
cd app-backend
mvn spring-boot:run
```

后端服务将在 http://localhost:8080/api 启动

### 前端启动

1. **安装依赖**
```bash
cd app-frontend
npm install
```

2. **启动开发服务器**
```bash
npm run dev
```

前端服务将在 http://localhost:3000 启动

## API文档

### 用户接口
- POST `/api/user/login` - 用户登录
- POST `/api/user/register` - 用户注册
- GET `/api/user/{userId}` - 获取用户信息
- PUT `/api/user/update` - 更新用户信息

### 商品接口
- GET `/api/product/page` - 分页查询商品
- GET `/api/product/{productId}` - 获取商品详情

### 购物车接口
- POST `/api/cart/add` - 添加到购物车
- PUT `/api/cart/update` - 更新购物车数量
- DELETE `/api/cart/remove` - 删除购物车商品
- GET `/api/cart/list` - 获取购物车列表
- DELETE `/api/cart/clear` - 清空购物车

### 订单接口
- POST `/api/order/create` - 创建订单
- GET `/api/order/detail` - 获取订单详情
- GET `/api/order/list` - 获取订单列表
- PUT `/api/order/cancel` - 取消订单
- PUT `/api/order/confirm` - 确认收货

### AI接口
- POST `/api/ai/chat` - 智能客服对话
- POST `/api/ai/recommend` - 商品推荐
- POST `/api/ai/optimize-description` - 优化商品描述

## 默认账号

管理员账号:
- 用户名: admin
- 密码: admin123

## 注意事项

1. **数据库配置**: 确保MySQL已启动并创建了shop_db数据库
2. **Redis配置**: 确保Redis已启动
3. **AI功能**: 如需使用AI功能,需要在application.yml中配置有效的OpenAI API Key
4. **端口占用**: 后端使用8080端口,前端使用3000端口,确保端口未被占用

## 开发说明

- 所有代码注释均使用中文
- 遵循RESTful API设计规范
- 前端采用组件化开发
- 使用JWT进行身份认证
- 统一响应格式封装

## 许可证

MIT License

## 作者

Lingma

---

如有问题,请提交Issue或联系开发者。
