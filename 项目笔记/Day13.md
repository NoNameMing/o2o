###ShopCategoryDao

####ShopCategoryDao.java --- 接口

```java
package com.ming.o2o.dao;

import com.ming.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
                                                    ShopCategory shopCategoryCondition);

}

```



####ShopCateogryDao.xml --- MyBatis 配置

如果获取的 parent_id 不为空，就添加进数据库中。

```xml-dtd
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.ming.o2o.dao.ShopCategoryDao">
    <select id="queryShopCategory" resultType="com.ming.o2o.entity.ShopCategory">
        SELECT
        shop_category_id,
        shop_category_name,
        shop_category_desc,
        shop_category_img,
        priority,
        create_time,
        last_edit_time,
        parent_id,
        FROM
        tb_shop_category
        <where>
            <if test="shopCategoryCondition.parent != null">
                and parent_id = #{shopCategoryCondition.parent.shopCategoryId}
            </if>
        </where>
        ORDER BY
        priority DESC
    </select>
</mapper>
```

where 条件的作用：传入相关参数去筛选想要的内容。



####ShopCategoryDaoTest.java

```java
package com.ming.o2o.dao;

import com.ming.o2o.BaseTest;
import com.ming.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory(){
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(1, shopCategoryList.size());
    }
}

```



####Test 失败问题

![屏幕快照 2019-09-23 上午8.29.12](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-23 上午8.29.12.png)



**原因：parent_id 后不需要逗号，语法错误。**

**解决：去掉。**





