# springcloud

## 平台简介
这是一套快速开发框架，采用Spring cloud alibaba作为基础框架，包含主流工具套件

## 系统模块
com.cddx
├── gateway         // 网关模块 [8080]
├── common          // 通用模块
│   └── common-core                         // 核心模块
│   └── common-datasource                   // 多数据源
│   └── common-redis                        // 缓存服务
├── models          // 实体模块
│   └── common-base                         // 基础实体
├── modules         // 业务模块
│   └── file                                // 文件服务 [9300]
├──pom.xml                // 公共依赖

## 程序架构