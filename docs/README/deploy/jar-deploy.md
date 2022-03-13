# Jar 包部署

## 环境要求

1. Java 11+
2. Mysql

## 部署流程

1. 在 [Github RELEASE](https://github.com/vran-dev/databasir/releases) 页面下载最新版应用 Databasir.jar (你也可以选择克隆项目后自行构建)
2. 将 Databasir.jar 上传到服务器
3. 在 Databasir.jar 所在目录创建 config 目录，并在目录下创建 `application.properties` 配置，配置中配置 MYSQL 的用户名、密码和连接

```properties
# 端口号，默认 8080
server.port=8080
# 数据库用户名
databasir.db.username=root
# 数据库密码
databasir.db.password=123456
# 数据库地址
databasir.db.url=127.0.0.1:3306
```

4. 通过 `java -jar Databasir.jar` 启动应用即可

## 登录验证

应用启动完成后会默认创建 Databasir 管理员用户

- 用户名：databasir
- 密码：databasir

通过该账号登录应用既可以进行管理