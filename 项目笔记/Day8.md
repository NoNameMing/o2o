# (package) enums 枚举类包

###作用，也是构建逻辑

- 存放使用的枚举；

- 实现获取枚举值的功能。



###知识点

****

就按照今天构建 ShopStateEnum.java 思路来说。

#####1.首先是一个 Java 枚举类；

构建方式如下：

```java
public enum ShopStateEnum{

}
```



#####2.使用构造函数实现一个枚举中的多状态；

和普通构建构造函数是一个方法。

```java
private ShopStateEnum(int state, String stateInfo) {
  this.state = state;
  this.stateInfo = stateInfo;
};
```



#####3.枚举状态表示法；

这和构造函数有关，构造函数什么格式，枚举对应它的格式就对了。

```java
CHECK(0, "审核中"), OFFLINE(-1, "非法店铺"), SUCCESS(1, "操作成功"),
PASS(2, "通过认证"), INNER_ERROR(-1001, "内部系统错误"), NULL_SHOPID(-1002, "ShopId为空");
```



#####4.枚举类中的函数；

首先声明的方法如下：

```java
// 枚举函数名前先写枚举类名
public static ShopStateEnum stateOf(int state) {

}
```



##### for 循环的新方式

```java
for (ShopStateEnum stateEnum : values()) {}
```



##### get方法的使用理解

当你需要一个类的参数时，你可以通过如下步骤来获取相应的参数：

- 实例化类；
- 使用实例化好的类调用 getParameter 方法；

这样，你就可以获得相关参数，因为 getParameter 方法返回的就是你想要的参数。

```java
public static ShopStateEnum stateOf(int state) {
  			// 实例化枚举类 ShopStateEnum
        for (ShopStateEnum stateEnum : values()) {
            // 调用 stateEnum 方法获取相关参数
            if (stateEnum.getState() == state) {
                return stateEnum;
            }
        }
        return null;
}
```





# (package) dto 

###名称

dto 数据传输对象 Data Transfer Object

dao 数据访问对象 Data Access Object

###作用

封装数据，面向 UI（来源于 csdn，可能不准确），也有说面向 Model 层的



##今天构建的类 

####ShopExecution.java

#####参数

其中有构建店铺时相关的数据：结果状态、状态标记、店铺数量；

还有 Shop shop 用于增删改查；

List<Shop> 存放查询店铺列表。



#####构造器

**构造器，也就是构造函数。参数不一样，就是功能不同的构造函数。**



# (package) util包

###作用

故名思义，工具包



###今天构建的类

####ImageUtil.java && PathUtil.java

#####PathUtil.java

是为了适应不同操作系统路径使用的。



#####ImageUtil.java

给图片加水印，但是实现起来比较复杂。

我没有理解。