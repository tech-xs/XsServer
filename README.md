### [官网](https://x-s.tech/)
### [服务端代码](https://github.com/xs-sjw/SxWeb)
### [网页端代码](https://github.com/xs-sjw/XsServer)


## 启动项目流程

> **1.运行环境**\
> **2.新建配置文件**\
> **3.新建数据库**\
> **4.通过Maven下载依赖包**\
> **5.运行项目**

### 1.运行环境

> **1. JDK11**\
> **2. Redis**\
> **3. MySql**\
> **4. Maven**

### 2.运行环境

> **1.找到项目启动的模块**\
> **2.在项目模块中的resources文件夹中新建[application-dev-local.yml]配置文件**\
> **3.修改配置文件**

**配置文件示例**\
**请将哦配置文件中的数据库和Redis配置换成您自己的配置**\
**端口为 7788**

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://127.0.0.1:3306/xs_tech?useUnicode=true&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8&nullCatalogMeansCurrent=true&useSSL=false
    username: root
    password: "xxx"

  redis:
    host: 127.0.0.1
    port: 6379
    database: 1
#    password: xxx
```

### 3.新建数据库

**通过创建数据库命令新建项目使用的数据库,数据库名需要与配置文件中的数据库名匹配**

```sql
CREATE
DATABASE xs_tech CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 4.通过Maven下载依赖包

**在项目根目录下执行 maven install 命令**

```
maven install
```

### 5.运行项目

> 找到模块中的[Application.java]启动文件直接其他项目即可\
> 如果没有新建项目模块可以使用[xs-tech-examples]模块[ExamplesApplication.java]启动项目

### 6.初始用户名密码

> **用户名 admin**\
> **密码 111111**

### 7.文档查看

**通过网页端登陆后台,在文档管理中查看跟多文档**
