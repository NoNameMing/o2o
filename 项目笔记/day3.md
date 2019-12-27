###pom.xml中遇到的配置问题

报错：cannot resolve 我在 dependency 标签中配置对中的 groupId，包括 org.mybatis、c3p0；

解决：添加我们国内的 ali maven 仓库；

代码：

```html
  <repositories>
    <repository>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    </repository>
    <repository>
          <id>jcenter</id>
          <name>central</name>
          <url>http://jcenter.bintray.com/</url>
    </repository>
  </repositories>
```

原因：其实这个仓库是一天前就添加好的，但是今天才起作用。在此期间我还 reimport 了很多次。或许是它们昨天在维护、或者是自己昨天网络太慢了。不过这只是我的猜测，并不准确。



###xmlns

命名空间；

最大的体会就是在配置不同功能的 xml 文件时需要不同的 xmlns，即命名空间。



###xml文件配置流程 ------ 自底向上配置思路

#####pom.xml

**作用：配置项目所需jar包；**



#####jdbc.properties

**作用：配置连接数据库相关的用户名、密码、端口号、驱动；**

```xml
jdbc.driver = com.mysql.jdbc.Driver
jdbc.url = jdbc:mysql://localhost:3306/o2o?useUnicode=true&characterEncoding=utf8
jdbc.username=root
jdbc.password=0085a
```





#####mybatis-config.xml

**作用：配置mybatis全局属性，如数据库连接方式；**

配置开头代码，一般是复制粘贴：

```html
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
```

**xml 我没有学过，不过我的感觉是它类似于游戏中的设定。单词都一样 --- settings**

configuration标签中做的配置：

```xml
    <!--  配置全局属性  -->
    <settings>
        <!--使用jdbc 的 generatedKeys获取数据库自增主键的值-->
        <setting name="useGeneratedKeys" value="true"/>
        <!-- 使用列标签替换列别名 默认：true-->
        <setting name="useColumnLabel" value="true"/>
        <!-- 开启驼峰命名转换 默认：true-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
```



#####spring-dao.xml

**作用：加载 mybatis 数据库配置；**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/spring-context.xsd">
    <!-- 1.配置数据库相关参数properties的属性 -->
    <context:property-placeholder location="classpath:jdbc.properties" />

    <!-- 2.数据库连接池 -->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <!-- 配置连接池属性 -->
        <property name="driverClass" value="${jdbc.driver}" />
        <property name="jdbcUrl" value="${jdbc.url}" />
        <property name="user" value="${jdbc.username}" />
        <property name="password" value="${jdbc.password}" />

        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30" />
        <property name="minPoolSize" value="10" />
        <!-- 关闭连接后不自动 commit -->
        <property name="autoCommitOnClose" value="false" />
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000" />
        <!-- 获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2" />
    </bean>

    <!-- 3.创建数据库连接池的对象 SqlSessionFactory -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean" >
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 配置 MyBatis 的全局配置文件 mybatis-config.xml -->
        <property name="configLocation" value="classpath:mybatis-config.xml" />
        <!-- 配置 entity 包 -->
        <property name="typeAliasesPackage" value="com.ming.o2o.entity" />
        <!-- 扫描 sql 配置文件：mapper 需要的 xml 文件 -->
        <property name="mapperLocations" value="classpath:mapper/*.xml" />
    </bean>

    <!-- 4.配置扫描 Dao 接口包，动态实现 Dao 接口，注入到 spring 容器中 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 注入 sqlSessionFactory -->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
        <!-- 给出需要扫描 Dao 接口包 -->
        <property name="basePackage" value="com.ming.o2o.dao" />
    </bean>
</beans>

```



#####spring-service.xml

**作用：将 dao 中的配置注入到 service 层做操作；**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">
    <!-- 扫描 service 包下所有使用注解的类型 -->
    <context:component-scan base-package="com.ming.o2o.service" />

    <!-- 配置事务管理器 -->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager" >
        <!-- 注入数据库连接池 -->
        <property name="dataSource" ref="dataSource" />
    </bean>

    <!-- 配置基于注释的声明式事务 -->
    <tx:annotation-driven transaction-manager="transactionManager" />
</beans>
```





#####spring-web.xml

**作用：定义 controller 行为；**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd">
    <!-- 配置 spring-mvc -->
    <!-- 开启 spring-mvc 注解模式 -->
    <mvc:annotation-driven />

    <!-- 静态资源默认 servlet 配置 -->
    <mvc:resources mapping="/resources/**" location="/resources/" />
    <mvc:default-servlet-handler />

    <!-- 定义视图解析器 -->
    <bean id="viewResolver"
          class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/html/"></property>
        <property name="suffix" value=".html"></property>
    </bean>
    
    <!-- 扫描web中相关的bean -->
    <context:component-scan base-package="com.ming.o2o.web" />

</beans>
```

