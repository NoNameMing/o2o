#DAO层 --- 新增店铺

###学习目标

****

- 连接数据库
- MyBatis数据库映射关系的配置
- dao service controller 层代码的编写，Junit的使用
- 图片处理工具 --- Thumbnailator
- suimobile前端设计与开发



###零碎知识点

****

**1.标签怎么记？不需要记，标签是记不完的，因为不同的框架有不同的标签。但有一些是不会变的：驱动 driver、地址url、用户名user、密码password。剩下的就是站在设计者的角度思考，然后查询相关框架怎么使用即可；**

**2.MyBatis优势：支持动态 Sql 语句；**

**3.@Ignore 注释 可以让测试类不再跑这个测试方法**；

**4.MyBatis配置时在 #{} 中存属性；**

**5.DaoTest中实现方法时，来源于 类B 的属性要先实例化 类B，然后调用实例化的 类B 中的 set 方法设定属性，最后将这个实例化的 类B 写入本类的 set 方法的参数中；**

```java
ShopCategoryDao
ShopCategory shopCategory = new ShopCategory();
ShopCategoryDao
shopCategory.setShopCategoryId(1L);
// shop 中设定 setShopCategory
shop.setShopCategory(shopCategory);
```





###遇到的问题

****

- Sequel pro HOST地址没有改过的话应是：127.0.0.1；

- com.ming.o2o.dao.ShopDaoTest 测试第一次出现的问题在于在构建实体类时没有将一些变量名写成需要的名字。



###写了什么

****

####ShopDao --- Interface

#####作用：

写好接口，准备在 com.ming.o2o.dao.ShopDaoTest 中实现以验证功能；



####ShopDao.xml

#####作用：

写好 Sql 配置用于执行；

#####实现：

- mapper 标签中写好 namespace，是 Dao 层中的接口 ShopDao。mapper 中包裹 insert 和 update；
- insert 标签中写好该表主键两种命名的属性，keyColumn 和 keyProperty。keyColumn赋值表中列名shop_id，keyProperty赋值为驼峰名shopId；
- update 参数类型是实体类中的 Shop，由于 MyBatis 的灵活性。将需要更新的属性写入 set 标签中，需要时它就会调用。



####com.ming.o2o.dao.ShopDaoTest.java

##### 作用：

实现 ShopDao 接口中定义好的方法；

#####实现：

利用 entity 类中定义好的 set 方法，直接设定参数。之后 MyBatis 会通过 xml 中的 mapper 解读出这些信息，把信息的修改传递给数据库。



