###报错：Non-static method cannot be referenced from a static context

**分析：**在静态上下文中不能引用非静态方法；

**解决：**创建 ProductDao 实例。

![屏幕快照 2019-11-04 下午9.19.37](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-04 下午9.19.37.png)



###商品编辑后端开发下

####Service层 --- ProductService.java

- 通过商品 Id 查询唯一的商品信息；
- 修改商品信息并做图片处理；
- 