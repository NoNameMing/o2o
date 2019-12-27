package com.ming.o2o.service.impl;

import com.ming.o2o.dao.ProductDao;
import com.ming.o2o.dao.ProductImgDao;
import com.ming.o2o.dto.ImageHolder;
import com.ming.o2o.dto.ProductExecution;
import com.ming.o2o.entity.Product;
import com.ming.o2o.entity.ProductImg;
import com.ming.o2o.enums.ProductStateEnum;
import com.ming.o2o.exceptions.ProductOperationException;
import com.ming.o2o.service.ProductService;
import com.ming.o2o.util.ImageUtil;
import com.ming.o2o.util.PageCalculator;
import com.ming.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        // dto 层方法将页码转化为数据库的行码，并调用 dao 层查询方法取回指定页码的商品列表；
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        // 基于同样的查询条件返回该条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution productExecution = new ProductExecution();
        productExecution.setProductList(productList);
        productExecution.setCount(count);
        return productExecution;
    }

    @Override
    @Transactional
    // 1.处理略缩图，获取略缩图相对路径并赋值给 product
    // 2.往 tb_product 写入商铺信息，获取 productId
    // 3.结合 productId 批量处理商品详情图
    // 4.将商品详情图列表批量插入 tb_product_img 中
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException {
        // 空值判断
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架的状态
            product.setEnableStatus(1);
            // 若商品略缩图不为空则添加
            if(thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                // 创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if(effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败" + e.toString());
            }
            // 若商品详情图不为空则添加
            if(productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY_LIST);
        }
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    /**
     * 1.若缩略图参数有值，先处理缩略图，
     * 若原先存在缩略图则先删除缩略图再添加新图，
     * 之后获取缩略图相对路径并赋值给 product；
     *
     * 2.若商品详情图列表参数有值，
     * 对商品详情图列表进行同样的操作；
     *
     * 3.将 tb_product_img 下面的该商品原先的详情图全部删除
     *
     * 4.更新 tb_product 的信息
     */
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        // 空值判断
        if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            // 给商品设置上默认值
            product.setLastEditTime(new Date());
            // 若商品略缩图不为空且原有略缩图不为空则删除原有略缩图并添加
            if(thumbnail != null) {
                // 先获取一遍原有信息，因为原来的信息里有原图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if(tempProduct.getImgAddr() != null) {
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }
            // 如果有新存入的商品详情图，则将原有的删除，并添加新的图片
            if(productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            try {
                // 更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if(effectedNum <= 0) {
                    throw new ProductOperationException("更新商品信息失败：");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品信息失败：" + e.toString());
            }
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY_LIST);
        }
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        // 根据 productId 获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        // 删除原来的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        // 删除数据库里原有的图片信息
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加略缩图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 批量添加图片
     *
     * @param product
     * @param productImageHolderList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImageHolderList) {
         // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
         String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
         List<ProductImg> productImgList = new ArrayList<ProductImg>();

         // 遍历一遍图片去处理，并添加进 productImg 实体类中
         for (ImageHolder productImgHolder : productImageHolderList) {
             String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
             ProductImg productImg = new ProductImg();
             productImg.setImgAddr(imgAddr);
             productImg.setProductId(product.getProductId());
             productImg.setCreateTime(new Date());
             productImgList.add(productImg);
         }

         // 如果确实是图片需要添加的，就执行批量添加操作
         if (productImgList.size() > 0) {
             try {
                 int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                 if (effectedNum <= 0) {
                     throw new ProductOperationException("创建商品详情图片失败");
                 }
             } catch (Exception e) {
                 throw new ProductOperationException("创建商品详情图片失败：" + e.toString());
             }
         }
    }
}
