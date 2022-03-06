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
# 端口号，默认 8080
server.port=8080
# 数据库用户名
databasir.db.username=root
# 数据库密码
databasir.db.password=123456
# 数据库地址
databasir.db.url=127.0.0.1:3306
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
docker build -f Dockerfile -t databasir:v1 .
```

如果你本地有 Java 环境的话我建议通过 gradle 先编译项目再构建镜像，这样会更快
```shell
# 先编译项目
./gradlw api:build

# 再构建镜像
docker build -f local.Dockerfile -t databasir:v1 .
```

3. 启动项目

通过 Docker 启动项目也需要通过环境变量来配置数据库信息

- DATABASIR_DB_URL 数据库地址
- DATABASIR_DB_USERNAME 数据库用户
- DATABASIR_DB_PASSWORD 数据库密码

```shell
docker run --name databasir-demo  -e DATABASIR_DB_URL=local_default:3306 -e DATABASIR_DB_USERNAME=root -e DATABASIR_DB_PASSWORD=123456 databasir:v1
```

## Docker Compose 本地部署

TODO