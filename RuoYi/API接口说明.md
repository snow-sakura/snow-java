# API 接口文档使用说明

## 访问方式

启动项目后，在浏览器中访问以下地址即可查看 Swagger 风格的 API 文档：

### Swagger UI 界面
```
http://localhost/swagger-ui.html
```

### API Docs JSON
```
http://localhost/v3/api-docs
```

### 指定分组查看
- 系统管理接口: `http://localhost/swagger-ui.html?group=系统接口`
- 测试模块: `http://localhost/swagger-ui.html?group=测试模块`

## 已创建的接口模块

### 1. 系统登录接口 (`/api/auth`)
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出
- `GET /api/auth/info` - 获取用户信息
- `GET /api/auth/captcha` - 获取验证码
- `POST /api/auth/refresh` - 刷新Token

### 2. 用户管理接口 (`/api/system/user`)
- `GET /api/system/user/list` - 获取用户列表（分页）
- `GET /api/system/user/{userId}` - 获取用户详情
- `POST /api/system/user` - 新增用户
- `PUT /api/system/user/{userId}` - 修改用户
- `DELETE /api/system/user/{userIds}` - 删除用户
- `PUT /api/system/user/{userId}/resetPwd` - 重置用户密码
- `PUT /api/system/user/{userId}/changeStatus` - 修改用户状态

### 3. 角色管理接口 (`/api/system/role`)
- `GET /api/system/role/list` - 获取角色列表（分页）
- `GET /api/system/role/{roleId}` - 获取角色详情
- `POST /api/system/role` - 新增角色
- `PUT /api/system/role/{roleId}` - 修改角色
- `DELETE /api/system/role/{roleIds}` - 删除角色
- `GET /api/system/role/{roleId}/authUser/allocatedList` - 查询角色已授权用户
- `GET /api/system/role/{roleId}/authUser/unallocatedList` - 查询角色未授权用户
- `PUT /api/system/role/authUser/cancel` - 取消用户授权角色
- `PUT /api/system/role/authUser/cancelAll` - 批量取消用户授权
- `PUT /api/system/role/authUser/selectAll` - 授权用户选择

## 功能特性

✅ **Swagger UI 界面** - 美观的交互式文档界面
✅ **在线调试** - 可直接在浏览器中测试接口
✅ **参数说明** - 详细的参数描述和示例
✅ **分组管理** - 按模块分组展示接口
✅ **注解完整** - 使用 Springdoc 标准注解

## 使用说明

1. **启动项目**
   ```bash
   mvn spring-boot:run
   ```

2. **访问 Swagger UI**
   - 打开浏览器访问: `http://localhost/swagger-ui.html`

3. **测试接口**
   - 点击接口展开详情
   - 填写参数
   - 点击 "Execute" 执行测试

4. **查看响应**
   - 查看返回状态码
   - 查看响应 body
   - 查看响应头

## 注意事项

⚠️ 当前接口为演示接口，返回的是模拟数据
⚠️ 实际业务逻辑需要在 TODO 标记处实现
⚠️ 数据库配置已更新为: `Wxh123456`

## 扩展开发

如需添加新的接口模块：

1. 在 `com.ruoyi.web.controller.api` 包下创建新的 Controller
2. 使用 Springdoc 注解标注接口信息
3. 重启项目即可在 Swagger UI 中看到新接口

常用注解：
- `@Tag` - 接口分组标签
- `@Operation` - 接口操作说明
- `@Parameter` - 参数说明
- `@Parameters` - 多个参数说明
