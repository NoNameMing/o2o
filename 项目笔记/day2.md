###感悟

- 从这个老师建立一个类再建立一张表的方法中我体悟到了实体类的设计和数据表的创建是息息相关的，而这是怎么个相关法呢？

  我自己的理解：我们的项目肯定要从数据库交互，读写相关信息。这时候就需要项目和数据库中的相关表有数据类型一致的类做数据处理、与数据库交互。



###项目结构

#####com.ming.o2o.entity

实体类；

#####com.ming.o2o.web

存放 controller 控制器，相当于 structs 中的 action；

#####com.ming.o2o. service

 业务逻辑层；

#####com.ming.o2o. service.impl

实现业务；

#####com.ming.o2o.dao

数据库操作、文件读写操作；

#####com.ming.o2o.dto

弥补 entity 的不足；

#####com.ming.o2o.enums

枚举类型；

#####com.ming.o2o.interceptor

拦截器；

#####com.ming.o2o.util

存放工具；



###pom.xml 中的 spring 相关配置

#####spring-core

spring 框架基本的核心工具类，其它组件都要用到这个包中的类，它是其它组件的核心；

#####spring-beans

访问配置文件、创建及管理 bean 以及进行 Ioc/DI 操作相关的类；

类似 Java 中的反射机制；

#####spring-context

扩展 spring 核心，包括 instrumentation 组件和 validation 组件；

#####spring-jdbc

spring 处理 sql 用的 jar；

#####spring-tx

为 JDBC、HIBERNATE、JDO、JPA 提供一致声明式和编程式事务管理；

#####spring-web

spring 的 web 框架；

#####spring-webmvc

SpringMVC框架相关的类；

