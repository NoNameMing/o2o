###遇到的第一个问题 com.ming.o2o.service.AreaServiceTest.java 失败

原因：同之前的问题类型一样；在配置相关的 xml 文件时缺失了一部分声明。上一个是直接写错了，这一个是没写。那个讲师直接复制粘贴了，我跟着敲也没在意，还是自己对相关知识不熟悉。不知道该加什么、不该加什么。以及加上的，又是什么。

解决：加入声明；

```xml
       http://www.springframework.org/schema/tx
       http://www.springframework.org/schema/tx/spring-tx.xsd
```



