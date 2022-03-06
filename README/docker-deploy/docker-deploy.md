# Databasir 保姆级教程之 Docker 一键部署

[Databasir](https://github.com/vran-dev/databasir) 现在已经准备好了开箱即用的 Docker Image，你只需要简单两步就可以完成一个 Databasir 应用的构建



## 前提条件

1. 用户系统已经有了 Docker 环境
2. 已经有可以连接的 Mysql 数据库



## 安装步骤

1.  拉取最新版镜像，当然你也可以将 latest 替换成你想要得版本号，具体有哪些镜像版本可以拉取可以在[这里](https://registry.hub.docker.com/r/vrantt/databasir)查看

```shell
docker pull vrantt/databasir:latest
```



2. 镜像下载完成以后就可以直接启动了

```shell
docker run --name my-databasir -e DATABASIR_DB_URL=127.0.0.1:3306 -e DATABASIR_DB_USERNAME=root -e DATABASIR_DB_PASSWORD=123456 databasir:latest -p 8888:8080
```



这里解释一下各个命令参数

- `-- name` 代表镜像启动后的名称，你可以随意命名
- `-e` 指定环境变量，databasir 需要依赖 3 个环境变量，所以用 `-e` 指定了  3 个变量，它们分别是
  1. DATABASIR_DB_URL 数据库地址
  2. DATABASIR_DB_USERNAME 数据库账号名称
  3. DATABASIR_DB_PASSWORD 数据库密码

- `-p` 暴露端口，databasir 默认在容器内部开放了 8080 端口，这里将宿主机的 8888 端口映射到了容器的 8080 端口



启动后，Databasir 会默认创建一个超级管理员账户

- 用户名：databasir
- 密码：databasir

这时候访问 http://localhost:8888 进入登录页，输入上面的账号和密码即可成功登入。