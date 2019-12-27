

###报错：production operation.js

Invalid or unexpected token

**分析：**没有使用过 === ，我认为是 === 的问题；

**解决：**通过参考老师的代码，我发现这是一个三目运算符。在冒号后的代码应是空值''而非双引号。

![屏幕快照 2019-11-02 下午4.08.13](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-02 下午4.08.13.png)



###报错：productionoperation.js

Illegal invocation

**分析：**这个问题遇到过，在于打错了 ajax 中的一些关键字，通过对比 shopoperation.js 我发现错误居然一模一样。都是把 processData 写成了 processType。

**也找到一个小技巧，如果 ajax 里面写的是提交的参数，IDEA会把{}中的内容认作settings。而不是写错时的html。**

![屏幕快照 2019-11-02 下午4.27.01](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-02 下午4.27.01.png)

###商品编辑思路 -- 图片

传入新图片，就删除所有旧图片。



###商品编辑的三个功能点

- 提前获取商品信息；
- 传入新图时删除旧图；
- 提交后修改商品信息。



