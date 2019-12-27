package com.ming.o2o.service;

        import com.ming.o2o.dto.ImageHolder;
        import com.ming.o2o.dto.ShopExecution;
        import com.ming.o2o.entity.Shop;
        import com.ming.o2o.exceptions.ShopOperationException;
        import org.springframework.stereotype.Service;

        import java.io.File;
        import java.io.InputStream;

public interface ShopService {
        /**
         * 根据 shopCondition 分页返回相应店铺列表数据
         *
         * @param shopCondition
         * @param pageIndex
         * @param pageSize
         * @return
         */
        public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
        /**
         * 通过店铺 Id 获取店铺信息
         *
         * @param shopId
         * @return
         */
        Shop getByShopId(long shopId);

        /**
         * 更新店铺信息，包括店铺处理
         *
         * @param shop
         * @param thumbnail
         * @return
         * @throws ShopOperationException
         */
        ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

        /**
         * 注册店铺信息，包括店铺处理
         *
         * @param shop
         * @param thumbnail
         * @return
         * @throws ShopOperationException
         */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
