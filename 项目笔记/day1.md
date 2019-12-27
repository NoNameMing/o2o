#o2o笔记 Day1

###零碎

build 中可以保存 plugin



###动态网页部署方式 --- web.xml 中的配置

#####web.xml中开头的文件

```
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee     http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">
```



#####welcome-file-list标签

该标签中的 **welcome-file** 属性可以设置多个，部署项目时按标签顺序依次访问



###MySQL笔记

#####int 数据类型

int(字节长度)

| **类型**  | **字节** | **最小值**              | **最大值**              |
| --------- | -------- | ----------------------- | ----------------------- |
|           |          | **(带符号的/无符号的)** | **(带符号的/无符号的)** |
| TINYINT   | 1        | -128                    | 127                     |
|           |          | 0                       | 255                     |
| SMALLINT  | 2        | -32768                  | 32767                   |
|           |          | 0                       | 65535                   |
| MEDIUMINT | 3        | -8388608                | 8388607                 |
|           |          | 0                       | 16777215                |
| INT       | 4        | -2147483648             | 2147483647              |
|           |          | 0                       | 4294967295              |
| BIGINT    | 8        | -9223372036854775808    | 9223372036854775807     |
|           |          | 0                       | 18446744073709551615    |