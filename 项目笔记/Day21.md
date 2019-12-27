###报错：Java.lang.NullPointerException

**分析：**productExecution 是一个null 值，应该查看其返回值是不是默认地写了 null；

**解决：**果然如此，所以在 try 的成功串中 return 匹配的 productExecution 的构造方法即可。

![屏幕快照 2019-11-05 下午3.47.41](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-05 下午3.47.41.png)



###Controller 层的功能

- 根据 productId 返回 product 的信息；
- modifyProduct 方法 修改 product 信息。