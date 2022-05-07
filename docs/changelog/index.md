# changelog

## v1.0.4
feature

- feature：表文档中”可空”采用 YES/NO 展示
- feature：表文档中”默认值”为 null 时采用红色 tag 展示
- feature：支持上传本地驱动
- feature：文档侧边栏支持按颜色显示版本差异
- feature：点击分组卡片即可跳转到项目列表页
- feature：登录应用采用卡片替代表格展示
- feature：UI 细节优化，采用响应式布局

bug fix

- bug-fix：hive 同步表结构时出现异常导致失败
- bug-fix：oracle 同步表时数据为空
- bug-fix：自定义驱动配置连接属性未生效

ref

- refactor：重构版本差异接口逻辑

Full Changelog: [v1.0.3...v1.0.4](https://github.com/vran-dev/databasir/compare/v1.0.3...v1.0.4)

## v1.0.3
feature

- feature：重新设计同步任务列表
- feature：支持取消同步中的任务
- feature：文档页面添加【回到顶部】按钮
- feature：内置 mysql、oracle、sqlserver、postgresql、mariaDB 数据库模板
- feature：支持通过 JSON 导入数据库模板
- feature：创建分组、项目时的【描述】字段由必填改为可选
- feature：优化页面 UI 多个细节

bug fix

- bug-fix：并发同步任务时概率出现版本冲突
- bug-fix：markdown 文档导出时出现 NPE
- bug-fix：部分数据库（oracle、达梦）同步文档失败
- bug-fix：同步表超过 1000+ 时出现 cursor limit

Full Changelog: [v1.0.2...v1.0.3](https://github.com/vran-dev/databasir/compare/v1.0.2...v1.0.3)

## v1.0.2

feature

- feature；UML 图片导出支持 svg 格式
- feature；表目录列表支持按表名、注释名搜索
- feature；表文档页面新增注释展示
- feature；数据库扩展支持自动获取驱动类名
- feature：数据库元数据同步采用异步任务设计，解决大数据量同步超时问题
- feature：文档内容采用分组加载，解决大数据量加载超时问题
- feature：文档默认模板统一改为中文

security

- security：对上传的驱动 jar 包新增规则校验
- security：采用可配置（默认随机）的 jwt token secret

Full Changelog: [v1.0.1...v1.0.2](https://github.com/vran-dev/databasir/compare/v1.0.1...v1.0.2)

## v1.0.1
feature

- feature：重新设计文档模板编辑功能
- feature：优化文档页面样式

bug fix

- bug fix：邮件线程池配置导致系统启动失败
- bug fix：Diff 引擎异常导致文档同步失败
- bug fix：部分浏览器复制 Insert SQL 会出现不兼容情况

Full Changelog: [v1.0.0...v1.0.1](https://github.com/vran-dev/databasir/compare/v1.0.0...v1.0.1)