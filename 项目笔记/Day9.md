#Service 层的实现



### IDEA 操作方式

- 替换代码，本文件中使用 cmd + r；



###logback 报错的解决历程

XXX_IS_UNDIFINED解决历程；

- 首先是 log_pattern_IS_UNDEFINED，在发现之后，我就把属性添加到每一个 appender 中，而不是以全局变量的一个方式。发现可以正常打印日志。但是 maxHistory 的报错同 log_pattern，也是XXX_IS_UNDEFIEND。
- 然后我就去看 property 这些标签，我以为是没有写对位置。最后仔细对比发现我把 property 写成了 proerty。改正之后就没再有 logback 本身一大串的报错了。



