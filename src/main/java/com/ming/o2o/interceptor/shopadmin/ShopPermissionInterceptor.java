package com.ming.o2o.interceptor.shopadmin;

import com.ming.o2o.entity.PersonInfo;
import com.ming.o2o.entity.Shop;
import com.ming.o2o.util.HttpServletRequestUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 事前拦截
 *
 */

public class ShopPermissionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        Shop currentShop = new Shop();
        // 从 session 中获取当前的店铺
        Long shopId =  HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > 0) {
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
        }
        currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 从 session 中获取当前可操作的店铺列表
        @SuppressWarnings("unchecked")
        List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
        // 非空判断
        if(currentShop != null && shopList != null) {
            // 如果当前店铺在可操作的列表里返回 true，则进行接下来的用户操作
            for(Shop shop : shopList) {
                // 如果当前店铺在可操作的列表里返回 true，则进行接下来的用户操作
                if(shop.getShopId() == currentShop.getShopId()) {
                    return true;
                }
            }
        }
//         若不满足拦截器的验证则返回 false，终止用户的执行
        return false;
    }

}
