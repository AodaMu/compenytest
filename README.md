# 公司门户网站 - 后端API

本项目是一个基于Spring Boot开发的公司门户网站后端API服务，实现了API接口文档中规定的所有功能。

## 技术栈

- Spring Boot 3.3.2
- Spring Security + JWT
- Spring Data JPA
- MySQL数据库
- Maven

## 项目功能

- 轮播图管理：获取、添加、更新、删除轮播图
- 优惠政策管理：获取、添加、更新、删除优惠政策
- 产品管理：获取所有产品、获取热销产品、产品详情、添加、更新、删除产品
- 关于我们管理：获取和更新公司信息
- 联系我们管理：获取和更新联系方式
- Logo管理：获取和更新公司Logo
- 管理员账户：登录验证、密码修改
- 文件上传：支持图片等文件上传

## 数据库配置

```properties
spring.datasource.url=jdbc:mysql://test-db-mysql.ns-iory1dzh.svc:3306/company_portal?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=2cghz2jz
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

## 运行项目

1. 确保已安装Java 17或以上版本
2. 确保MySQL数据库已运行
3. 执行以下命令启动项目：

```bash
chmod +x entrypoint.sh
./entrypoint.sh
```

或者

```bash
./mvnw clean package -DskipTests
java -jar target/hello-0.0.1-SNAPSHOT.jar
```

## 接口地址

- 基础URL：`/api`
- 管理员接口URL：`/api/admin`
- 文件上传URL：`/api/upload`

## 管理员账户

- 默认用户名：admin
- 默认密码：admin123

## 项目目录结构

```
src/main/java/com/example/hello/
├── config/          - 配置类
├── controller/      - 控制器
├── dto/             - 数据传输对象
├── exception/       - 异常处理
├── model/           - 数据模型
├── repository/      - 数据访问层
├── security/        - 安全相关
├── service/         - 业务逻辑层
└── HelloApplication.java - 主启动类
``` # compenytest
