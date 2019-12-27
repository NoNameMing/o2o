package com.ming.o2o.service.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ming.o2o.cache.JedisUtil;
import com.ming.o2o.dao.ShopCategoryDao;
import com.ming.o2o.entity.HeadLine;
import com.ming.o2o.entity.ShopCategory;
import com.ming.o2o.exceptions.AreaOperationException;
import com.ming.o2o.exceptions.HeadLineOperationException;
import com.ming.o2o.exceptions.ShopCategoryOperationException;
import com.ming.o2o.exceptions.ShopOperationException;
import com.ming.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Autowired
    private JedisUtil.Keys jedisKeys;

    @Autowired
    private JedisUtil.Strings jedisStrings;
    private static Logger logger = LoggerFactory.getLogger(ShopCategoryServiceImpl.class);

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        // 定义 redis 的前缀
        String key = SCLISTKEY;
        // 定义接收对象
        List<ShopCategory> shopCategoryList = null;
        // 定义 jackson 数据转换类
        ObjectMapper mapper = new ObjectMapper();
        // 拼接出 redis 的 key
        if(shopCategoryCondition == null) {
            // 若查询条件为空，列出首页所有大类，即 parentId 为空所有类型
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
                && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            // 若 parentId 非空，则列出该 parentId 下的所有子类别
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
        } else if (shopCategoryCondition != null) {
            // 列出所有子类别，不管属于哪个类别，都列出来
            key = key += "_allsecondlevel";
        }
        // 判断 key 是否存在
        if(!jedisKeys.exists(key)) {
            // 若不存在，则从数据库中取出相应数据
            shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
            // 将相关实体类集合转换成 string，存入 redis 对应的 key 中
            String jsonString ;
            try {
                jsonString = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
            jedisStrings.set(key, jsonString);
        } else {
            // 若存在，则直接从 redis 里面取出相应数据
            String jsonString = jedisStrings.get(key);
            // 指定要将 string 转换成的集合类型
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, ShopCategory.class);
            try {
                shopCategoryList = mapper.readValue(jsonString, javaType);
            } catch (JsonParseException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            } catch (JsonMappingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new ShopCategoryOperationException(e.getMessage());
            }
        }
        return shopCategoryList;
    }
}
