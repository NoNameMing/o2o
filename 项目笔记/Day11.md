

##解决问题

####no getter for enable_status错误

分析：我猜测是配置问题，因为查看实体类时 get 方法是存在的；

解决：ShopDao.xml 这里 enable_status 中的内容写错了，#{中的内容应为enableStatus}

![屏幕快照 2019-09-21 上午9.12.12](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-21 上午9.12.12.png)



####NullPointerException错误：se null值

![屏幕快照 2019-09-21 上午9.46.47](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-21 上午9.46.47.png)



Debug信息如下：

分析：ShopExecution是空值；说明 ShopService 的 Impl 返回了一个空对象；到 ShopServiceImpl 类中发现我返回的默认为空值；

解决： ShopServiceImpl 的 addShop 返回 **new ShopExecution(ShopStateEnum.CHECK, shop);**

![屏幕快照 2019-09-21 上午9.46.34](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-21 上午9.46.34.png)



## 建立的新类

###Package Util 下的新工具类 HttpServletRequestUtil.java 

作用：从 request 中根据 key 获取信息做相关类型转换并返回；

实现：把基本数据类型都获取并返回一次；

```java
package com.ming.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    /**
     * 从 request 中获取一个 key 做返回，转化为 int 类型
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {
        try{
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 从 request 中获取一个 key 做返回，转化为 String 类型
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        try{
            String result = request.getParameter(key);
            if(result != null){
                result = result.trim();
            }
            if("".equals(result)){
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}

```



###com.ming.o2o.web.shopadmin 下的新类  ShopManagementController.java

#####registerShop 方法作用：

- 1.接收并转化相应参数，包括店铺信息以及图片信息
- 2.注册店铺
- 3.返回结果

```java
private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 1.接收并转化相应参数，包括店铺信息以及图片信息

        // 获取前端传来的字符串，转化为 Shop 实体类
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try{
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        // 处理图片逻辑
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext()); // 获取文件
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request; // 强制转换
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg"); // 前端传来
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        // 2.注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo owner = new PersonInfo();
            owner.setOwnerId(1L);
            shop.setOwner(owner);
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            ShopExecution se = shopService.addShop(shop, shopImgFile); // CommonsMultipartFile 不能直接转换为 File，需要绕开
            if(se.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺信息");
            return modelMap;
        }
        // 3.返回结果
    }
```





**CommonsMultipartFile 不能直接转换为 File，需要绕开，具体方法如下：**

#####inputStreamToFile.java

问题： 传入的 File 是 CommonsMultipartFile 类型的，需要转化为 File 类型；

方法： CommonsMultipartFile 源码中是有方法可以获取文件输入流InputStream 的，因此用 InputStream 将其转化为 File；

更好的方法：这样就满足了 addShop 方法的设计要求。但是有更好的方法，将 InputStream 传入，交给 ThumbNailators 处理即可。

```java
private static void inputStreamToFile(InputStream ins, File file) {
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = ins.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
             throw new RuntimeException("调用inputStreamToFile产生异常" + e.getMessage());
        } finally {
            try{
                if (os != null) {
                    os.close();
                } else {
                    ins.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("inputStreamToFile关闭IO产生异常" + e.getMessage());
            }
        }
    }
```



在 registerShop 方法中添加的语句：

```java
File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            try {
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
```



###Controller 层的改造