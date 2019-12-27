##Dao层验证中遇到的问题

- 编码时IDEA对注解的报错

  原因：pom.xml 中我对 junit4 配置的 scope 是 test，而不是全局；

  解决：把 com.ming.o2o.BaseTest.java 和 DaoTest.java 放到 pom.xml 中设定好的 test 目录下。

  

- 编译遇到的报错'http://www.springframework.org/spring-context.xsd', 原因为 1) 无法找到文档; 2) 无法读取文档; 3) 文档的根元素不是 <xsd:schema>。

  原因：在 spring-dao.xml 中把这句写错了，正确的配置行如下：

  ```
  http://www.springframework.org/schema/context/spring-context.xsd"
  ```



##DaoTest 思路整理

#####1.在 test 中先新建 baseTest 类：

使用注解的方式整合 spring 和 junit；

告诉 junit spring 配置文件的位置；

#####2.在 test 中新建 com.ming.o2o.dao.AreaDaoTest.java：

利用 junit 获取 area 表的表长信息。