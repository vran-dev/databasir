# 构建指引

## 前言

Databasir 采用了前后端分离的模式进行开发和部署，项目仓库地址分别位于

- 后端应用： https://github.com/vran-dev/databasir
- 前端应用： https://github.com/vran-dev/databasir-frontend

## 后端

**一、环境要求**

1. Java 11+
2. Mysql
3. Intellij IDEA

**二、主要框架**

1. Gradle：构建工具
2. Spring-boot：核心框架
    1. Web
    2. Security
3. Quartz：定时任务调度
4. JOOQ：ORM 框架
5. Lombok：代码生成
6. Mapstruct：代码生成
7. Flyway：数据库脚本管理

**三、项目结构**

项目结构采用了分层结构

- api：所有的 http 接口、定时任务都在该模块下
- common：主要包含通用异常定义、加解密算法工具
- core：包含了所有的业务核心逻辑，包括 service、request/response data、converter 等
- dao：针对数据库的操作类都在该模块下，其中 generated-src 包含了 jooq 生成的代码，src 主要包含了 dao 的实现类
- plugin：主要包含了 JDBC 工具类，包括但不限于将数据库的元数据解析为 Java 对象 、PDF 导出等

项目配置都放在 api/src/main/resources 目录下

前端静态文件放在 api/src/main/resources/static 目录下

项目数据库脚本放在 dao/src/main/resources/db/migration 目录下

**四、项目启动**

1、通过 Java Main 方法启动

修改或创建配置文件 `api/src/main/resources/application-local.properties`

```properties
server.port=8080
logging.level.org.jooq=INFO

# 必须：数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://localhost:3306/databasir
spring.jooq.sql-dialect=mysql

# 可选：flyway 配置
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

启动 DatabasirApplication（com.databasir.DatabasirApplication） 类，启动时需要加参数 `-Dspring.profiles.active=local`

启动完成后，可以通过 http://localhost:8080 访问

2、通过 Gradle 启动

与 Java 启动模式一样，首先需要准备配置文件

修改或创建配置文件 `api/src/main/resources/application-local.properties`

```properties
server.port=8080
logging.level.org.jooq=INFO
# 必须：数据库配置
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.url=jdbc:mysql://localhost:3306/databasir
spring.jooq.sql-dialect=mysql
# 可选：flyway 配置
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration
```

执行 Gradle 命令

```shell
./gradlew bootRun --args='--spring.profiles.active=local'
```

启动完成后，可以通过 http://localhost:8080 访问

## 前端

一、依赖

1. vue
2. vuex
3. element-plus
4. axios

二、运行

```shell
npm run serve
```

默认端口为 3000