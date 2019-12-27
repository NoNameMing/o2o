package com.ming.o2o.web.frontend;

import com.ming.o2o.dto.ShopExecution;
import com.ming.o2o.entity.Area;
import com.ming.o2o.entity.Shop;
import com.ming.o2o.entity.ShopCategory;
import com.ming.o2o.service.AreaService;
import com.ming.o2o.service.ShopCategoryService;
import com.ming.o2o.service.ShopService;
import com.ming.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表里的 ShopCategory 列表（一级或者二级），以及区域信息列表
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshopspageinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShopPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 试着从前端请求中获取 parentId
        long parentId = HttpServletRequestUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;
        // 根据 parentId 是否存在获取 ShopCategory 列表
        if (parentId != -1) {
            // 如果 parentId 存在，则取出该一级 ShopCategory 下的二级 ShopCategory 列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            try {
            // 如果 parentId 不存在，则取出所有一级 ShopCategory（用户在首页选择的是全部商品列表）
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList = null;
        // 获取区域信息列表
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
    }

    @RequestMapping(value = "/listshops", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取一页要显示的的数据条数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 非空判断
        if((pageIndex > -1) && (pageSize > -1)) {
            // 试着获取一级类别 Id
            long parentId = HttpServletRequestUtil.getLong(request, "parentId");
            // 试着获取特定二级类别 Id
            long shopCategoryId = HttpServletRequestUtil.getLong(request, "shopCategoryId");
            // 试着获取区域 Id
            int areaId = HttpServletRequestUtil.getInt(request, "areaId");
            // 试着获取模糊查询的名字
            String shopName = HttpServletRequestUtil.getString(request, "shopName");
            // 获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId, shopCategoryId, areaId, shopName);
            // 根据查询条件和分页信息获取店铺列表，返回总数
            ShopExecution shopExecution = shopService.getShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("count", shopExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName) {
        Shop shopCondition = new Shop();
        if(parentId != -1L) {
            // 查询某个一级 ShopCategory 下面的所有二级 ShopCategory 里面的店铺列表
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            // 查询某个二级 ShopCategory 下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1L) {
            // 查询某个 areaId 下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
        }
        if (shopName != null) {
            // 查询名字里包括 shopName 的店铺列表
            shopCondition.setShopName(shopName);
        }
        // 前端仅仅展示审核通过的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }


}
