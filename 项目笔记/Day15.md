##今日实现的功能

- 保证一些店铺信息不被用户修改，比如点名



####首先通过 shopId 获取 shop 信息

问题：

![屏幕快照 2019-09-28 上午9.10.09](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-28 上午9.10.09.png)

原因：DEBUG 发现这里发现 getLong 方法的 key 写错了，所以一直都是 -1。



##昨日实现的功能

实现**修改店铺信息**。

逻辑上就是分为两步，**第一步查询相关信息**，**第二步修改信息**。

####1.配置ShopDao.xml

修改的 SQL 已经在 ShopDao.xml 中配置过。

而应查询的信息是更新的，如下：

```xml
<select id="queryByShopId" resultMap="shopMap" parameterType="Long">
        SELECT
        s.shop_id,
        s.shop_name,
        s.shop_desc,
        s.shop_addr,
        s.phone,
        s.shop_img,
        s.priority,
        s.create_time,
        s.last_edit_time,
        s.enable_status,
        s.advice,
        a.area_id,
        a.area_name,
        sc.shop_category_id,
        sc.shop_category_name
        FROM
        tb_shop s,
        tb_area a,
        tb_shop_category sc
        WHERE
        s.area_id=a.area_id
        AND
        s.shop_category_id=sc.shop_category_id
        AND
        s.shop_id=#{shopId}
    </select>
```

**注意**

- 各表命名方式，如 **tb_shop s**，调用属性就是 **s.shop_id**；
- 表与表之间的联系，连接词用 **WHERE**、**AND**，通过属性连接就是 **s.area_id=a.area_id**；



####2.service 层添加新功能

#####Shop getByShopId(long shopId);

通过店铺 Id 获取店铺信息



#####ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

更新店铺信息，包括店铺处理



####3.ShopServiceImpl 实现新方法

#####Shop getByShopId(long shopId);

```java
@Override
public Shop getByShopId(long shopId) {
  return shopDao.queryByShopId(shopId);
}
```

在 ShopDao.xml 已经配置过了相关方法，所以直接返回即可。

**（匹配名就在 ShopDao.xml 中的 select id 中配置）**



#####ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException;

```java
@Override
public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName) throws ShopOperationException {
  if (shop == null || shop.getShopId() == null) {
    return new ShopExecution(ShopStateEnum.NULL_SHOP);
  }
  // 1.判断是否需要处理图片
  try {
    if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
      Shop tempShop = shopDao.queryByShopId(shop.getShopId());
      if (tempShop.getShopImg() != null) {
        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
      }
      addShopImg(shop, shopImgInputStream, fileName);
    }
    // 2.更新店铺信息
    shop.setLastEditTime(new Date());
    int effectedNum = shopDao.updateShop(shop);
    if (effectedNum <= 0) {
      return new ShopExecution(ShopStateEnum.INNER_ERROR);
    } else {
      shop = shopDao.queryByShopId(shop.getShopId());
      return new ShopExecution(ShopStateEnum.SUCCESS, shop);
    }
  } catch (Exception e) {
    throw new ShopOperationException("modifyShop error:" + e.getMessage());
  }
}
```



**ImgUtil中匹配的方法**

```java
/**
* storePath 是文件路径还是目录路径
* 如果 storePath 是文件路径则删除文件路径
* 如果 storePath 是目录路径则删除该目录下所有文件
* @param storePath
*/
public static void deleteFileOrPath(String storePath) {
  File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
  if(fileOrPath.exists()) {
    if(fileOrPath.isDirectory()) {
      File files[] = fileOrPath.listFiles();
      for(int i = 0; i < files.length; i++) {
        files[i].delete();
      }
    }
    fileOrPath.delete();
  }
}
```



**RequestMapping是一个灵魂，里面放的是可以叫路由。通过这个路由可以访问、获取相关信息**

```url
http://localhost:8080/o2o/shopadmin/getshopbyid?shopId=1
```