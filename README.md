# Databasir
## 规划
项目目前还属于 MVP （可行性验证）阶段，功能处于随时调整的阶段

以下功能尚在开发中

- [ ] 表字段协同注释
- [ ] 操作审计日志

## 简介

**Databasir** 是一款在线数据库文档管理工具，为企业开发中最核心的数据库结构提供文档化管理支撑能力

1. 自动化：定时、手动同步数据库结构并生成文档
2. 版本化：历史变更版本皆可查看
3. 精细化：团队成员可以协同为文档做更精细化的注释
4. 扁平化：权限管理扁平，减少冗余流程，价值最大化

## 部署

Databasir 采用了前后端分离的模式进行开发和部署，前端和后端可以独立部署，也可以采用只部署已整合前端资源的后端应用

- 后端应用： https://github.com/vran-dev/databasir
- 前端应用： https://github.com/vran-dev/databasir-frontend

### JAR 模式部署

注意：

1. 使用 JAR 模式部署需要系统环境有 Java 环境，最低版本为 Java11。
2. 应用使用 MYSQL 作为数据存储，所以也需要准备好数据库。

部署：
1. 在 [Github RELEASE](https://github.com/vran-dev/databasir/releases) 页面下载最新版应用 Databasir.jar (你也可以选择克隆项目后自行构建)
2. 将 Databasir.jar 上传到服务器
3. 在 Databasir.jar 所在目录创建 config 目录，并在目录下创建 `application.properties` 配置，配置中配置 MYSQL 的用户名、密码和连接

```properties
# 端口号，默认8080
server.port=8080
# 数据库用户名
databasir.datasource.username=root
# 数据库密码
databasir.datasource.password=123456
# 数据库地址
databasir.datasource.url=127.0.0.1:3306
```

4. 通过 java -jar Databasir.jar 启动应用即可

应用启动后会默认创建 Databasir 管理员用户

- 用户名：databasir
- 密码：databasir

通过该账号登录应用既可以进行管理

### Docker 部署

TODO


## 展示

- 首页

![](README/home.jpg)

- 项目中心

![](README/group-projects.jpg)

- 项目创建

![](README/group-project-create.jpg)

- 项目文档

![](README/group-project-document.jpg)

- 分组成员

![](README/group-member-list.jpg)

- 添加成员

![](README/group-member-add.jpg)

- 用户中心

![](README/user.jpg)

- 个人中心

![](README/user-profile.jpg)

- 系统邮件

![](README/sys-mail.jpg)
