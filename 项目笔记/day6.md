#遇到的问题



##Tomcat 配置好 configuration 却无法 RUN 的问题

###问题属性：

对 IDEA 环境不熟悉引起的问题，这是一个小白问题；

###解决方式：

在配置 configuration 之前呢，在 configuration 左上角 或者 使用快捷键 CMD + N 先新建一个 Tomcat Template。这样在配置界面就会有一个 Tomcat，可供项目 RUN。



##部署项目到 Tomcat 时遇到的 permission denied 问题

###问题属性：

权限没有给到位；

###解决方式：

看到给出的目录是 Tomcat/bin 下 Catalina 的 Error 13, Permission Denied.

所以解决的方式是终端打开该目录，然后使用如下命令解决。

```shell
sudo chmod 777 catalina.sh
```



###验证 controller 时的 404

####问题属性：

个人代码错误，没有在



#对该项目的基本解读

###项目逻辑

讲师：“通过 SSM 实现功能是很简单的。按照规定，我们只需要在 DAO 创建我们想要的对象的接口；定义相关方法；并且在 Mapper 的 source folder 下创建出一一对应的 xml 文件来，这个 xml 方法中有我们所用到的 sql 语句 ；在 service 层中调用 dao 层方法返回想要的数据到 controller 中；controller 获取到数据之后把数据返回到前台。 ”



###涉及到的标签：

##### dao层：

**只是一个接口，不需要标签**；



#####service 层：

**除了接口之外还需要一个实现类**；

- @Service：

表示 这是 Service 层的一个实现类；在需要用到的地方，Spring 容器负责将它注入进来；



- @Autowired：

告诉 Spring 这里想用到某一个对象，请你注入进来；



#####controller 层： 

**之前的基础上再多两个标签**；

- @Controller：

表示 这是 Controller 层的一个实现类；在需要用到的地方，Spring 容器负责将它注入进来；



- @RequestMapping：

用来指定路由



- @Autowired：

告诉 Spring 这里想用到某一个对象，请你注入进来；



- @ResponseBody

告诉  controller 你返回的数据对象要自动转化为 json； 



###测试

#####com.ming.o2o.BaseTest

- @RunWith(SpringJUnit4ClassRunner.java)

告诉 Spring 我们要用 SpringJUnit4ClassRunner 这个类跑单元测试；



- ContextConfiguration

告诉 Spring 我们在类启动时加载相关 xml 做服务（spring-dao.xml）、做配置（spring-service.xml）



#####com.ming.o2o.dao.AreaDaoTest & com.ming.o2o.service.AreaServiceTest

- @Test

跑测试；



#SSM 重点知识

###DispatcherServlet 前端控制器 --- MVC 核心

作用：拦截符合要求的外部请求，并把请求分发到不同的控制器中去。

**调用步骤是面试常见问题**



###Spring IOC 和 Spring Aop

**IOC：**负责控制对象生命周期和对象间的关系的；类似媒婆。具体是通过 vi？？（没听懂） 和 依赖注入实现的；



**AOP：**面向切面编程；

实现：动态代理；

**AOP也是面试常见问题**



###MyBatis重点

**ORM：**通过描述对象和数据库之间映射的元数据，将程序中的对象自动持久化到关系数据库中。



#Logback 日志配置

###功能

- 故障定位；
- 显示程序运行状态；



###主要模块

#####logback - acess

与 service ？？（没听清）容器集成，通过 http 访问日志的功能； 



#####logback - classic

方便更换日志系统；



#####logback - core

给前两个模块提供基础服务；



###主要标签

#####logger

日志记录器。存放日志对象、定义日志类型、级别等；



#####appender 

指定日志输出目的地，它也就是输出的媒介；

媒介可以是控制台、可以是文件、可以是远程套接字服务器；



#####layout

格式化日志信息的输出的；



