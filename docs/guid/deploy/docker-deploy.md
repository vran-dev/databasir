# Docker 部署

[Databasir](https://github.com/vran-dev/databasir) 已经准备好了开箱即用的 Docker Image，你只需要简单三步就可以完成一个 Databasir 应用的部署

## 环境要求

1. Docker
2. Mysql 5.7+

## 测活接口

```shell
GET /live
```
返回 200 即表示启动成功

## 部署流程

1.  拉取镜像（更多版本[点击这里](https://registry.hub.docker.com/r/vrantt/databasir)查看）

```shell
docker pull vrantt/databasir:latest
```

2. 在 Mysql 中创建数据库 databasir

```sql
create database databasir;
```

3. 启动镜像，需要指定可用的 Mysql 的连接信息

```shell
docker run -p 8888:8080 --name my-databasir -e DATABASIR_DB_URL=127.0.0.1:3306 -e DATABASIR_DB_USERNAME=root -e DATABASIR_DB_PASSWORD=123456 vrantt/databasir:latest
```



**docker 启动命令参数说明**

| 参数                               | 说明                                                         | 必填 |
| ---------------------------------- | ------------------------------------------------------------ | ---- |
| --name my-databasir                | 启动的镜像名称                                               |      |
| -e DATABASIR_DB_URL=127.0.0.1:3306 | 数据库连接地址                                               | 是   |
| -e DATABASIR_DB_USERNAME=root      | 数据库连接用户名                                             | 是   |
| -e DATABASIR_DB_PASSWORD=123456    | 数据库连接密码                                               | 是   |
| -e DATABASIR_JWT_SECRET=databasir  | 生成用户登录 Token 的秘钥，如果部署了多个实例，那多个实例之间的秘钥要保持一致。默认为 UUID | 否   |
| -p 8888:8080                       | 将 databasir 的 8080 端口映射到宿主机的 8888 端口            |      |

## 登录验证

启动后，Databasir 会默认创建一个超级管理员账户

- 用户名：`databasir`
- 密码：`databasir`

这时候访问 http://localhost:8888 进入登录页，输入上面的账号和密码即可成功登入，到此就算部署完成
