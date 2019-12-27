###问题：无法获取服务 modifyproduct

**分析：**对比 js 代码，modifyproduct 的 url 并没有给 productId 赋值，查看后端代码发现是我后端 @RequestMapping 参数写成了驼峰命名。

###![屏幕快照 2019-11-07 下午2.53.08](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-07 下午2.53.08.png)



### 问题：传入后台的 currentShop 为空

**分析：**反复查看 js 文件，代码中并没有对 shop信息 的获取；

对比 addproduct 和 modifyproduct 的 controller 方法发现二者获取 shop 方式也一样，addproduct 的 js 方法中也没有对 shop 的获取。

通过查阅老师的实现方法，我发现他在实现 addproduct 方法中访问了http://localhost:8080/o2o/shopadmin/shopmanagement?shopId=1。

结合注释就明白了，shopId 存在了 session 中，不以 shopId = 1访问 session没法获取 shop 信息。

![屏幕快照 2019-11-07 下午3.32.49](/Users/wangxiaoming/Desktop/屏幕快照 2019-11-07 下午3.32.49.png)