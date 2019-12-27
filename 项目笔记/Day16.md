##遇到的问题

###ShopDaoTest - testQueryShopList()方法

**报错**：![屏幕快照 2019-10-09 上午9.42.30](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-09 上午9.42.30.png)

**分析**：定位可以定位到 owner_id 的设定位置，而且是 bad SQL grammer 错误，这说明其实是语法错误；

**修正**：

```xml
s.owner_id = #{shopCondition.owner.ownerId}
```

![屏幕快照 2019-10-09 上午9.45.16](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-09 上午9.45.16.png)



###ShopDaoTest - testCombineQueryShopList()方法

**报错：**

![屏幕快照 2019-10-09 上午11.50.40](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-09 上午11.50.40.png)

**分析：**

根据报错，shop_category_id 的来源不明确。

**解决：**

![屏幕快照 2019-10-09 上午11.51.30](/Users/wangxiaoming/Desktop/屏幕快照 2019-10-09 上午11.51.30.png)

给缺失主人的 parameter 加属性。



## 做什么

**列表展示的Dao层，配置 SQL 语句。要做到能联合查询。**