##遇到的错误

###getQueryString is not defined --- shopmanagement.js

![屏幕快照 2019-10-15 下午3.12.47](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-15 下午3.12.47.png)

**报错：**getQueryString 方法没有定义

**原因：**没有在 shopManage.html 加载 common.js 方法，仅仅加载了 shopmanage.js方法，而 getQueryString 方法来源于 common.js



### parameter not found --- productCategoryDaoTest.java

**报错：**属性 shop_category_name 不能为空值；

**分析：**先看 mapper 语句中是否有错，发现并没有；随后发现是 测试类 中做实例化对象时，本应写成 profuctCategory1 的对象写成了 productCategory，造成了 productCategory 属性的缺失。

![屏幕快照 2019-10-15 下午5.19.17](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-15 下午5.19.17.png)