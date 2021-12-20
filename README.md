# springcloud

## 平台简介
这是一套快速开发框架，采用Spring cloud alibaba作为基础框架，包含主流工具套件

## 系统模块

~~~
com.cddx
├── gateway         // 网关模块 [8080]
├── common          // 通用模块
│   ├── common-api                    // 公共feign接口
│   ├── common-core                   // 核心模块
│   ├── common-datascope              // 数据权限
│   ├── common-datasource             // 多数据源
│   ├── common-log                    // 日志服务
│   ├── common-redis                  // 缓存服务
│   ├── common-security               // 安全模块
│   └── common-swagger                // 系统接口
├── server          // 业务服务模块
│   ├── auth                          // 认证中心    [9200]
│   ├── file                          // 文件服务    [9300]
│   └── system                        // 系统服务    [9201]
├── visual          // 图形化管理模块
│   └── monitor                       // 监控中心    [9100]
└──pom.xml          // 公共依赖
~~~

## 程序架构