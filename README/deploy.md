# 部署指引

## 前言

Databasir 采用了前后端分离的模式进行开发和部署，项目仓库地址分别位于

- 后端应用： https://github.com/vran-dev/databasir
- 前端应用： https://github.com/vran-dev/databasir-frontend

当前提供了 Jar 和 Docker 两种部署方式

### JAR 模式部署

**一、环境要求**

1. Java 11+
2. Mysql

**二、部署流程**

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

**三、系统登陆**

应用启动后会默认创建 Databasir 管理员用户

- 用户名：databasir
- 密码：databasir

通过该账号登录应用既可以进行管理



### Docker 部署

目前镜像没有上传到 DockerHub，需要用户自己在本地手动构建

1. 克隆仓库

```shell
git clone https://github.com/vran-dev/databasir.git
```

2. 构建镜像

```shell
docker build -t databasir:v1 .
```

3. 启动项目

```shell
docker run  -e JAVA_OPTS="-Ddatabasir.datasource.url=127.0.0.1:3306 -Ddatabasir.datasource.username=root -Ddatabasir.datasource.password=123456" -p 8080:8080 -d databasir:v1
```

## Docker Compose 本地部署

TODO