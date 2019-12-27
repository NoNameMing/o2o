#图片处理

###路径处理 PathUtil.java

```java
public class PathUtil {
    // 获取系统属性，以设定适应路径
    private static String seperator = System.getProperty("file.seperator");
		
    // 返回项目图片的根路径
    public static String getImgBasePath(){
      	// 获取系统名
        String os = System.getProperty("os.name");
        String basePath = "";
        // windows 操作方式
        if(os.toLowerCase().startsWith("win")){
            basePath = "D:/projectdev/image/";
        } else {
          // mac linux 操作方式
            basePath = "/Users/wangxiaoming/Desktop/o2o/img/";
        }
        // 处理不同系统下的路径
        basePath = basePath.replace("/", seperator);
        return basePath;
    }
  
		// 返回项目图片的子路径
    public static String getShopImagePath(long shopId){
        
        String imagePath = "upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }
}
```



###图片处理 ImageUtil.java

```java
package com.ming.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    /**
     * 将 CommonsMultipartFile 转化为 File 类
     * @param cFile
     * @return
     */
    public static File transferCommomsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 处理缩略图并返回新生成图片的相对值路径
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(File thumbnail, String targetAddr) {
        // 获取随机名
        String realFileName = getRandomFileName();
        // 获取扩展名
        String extension = getFileExtension(thumbnail);
        makeDirPath(targetAddr);
      	// 组合生成相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relative addr is:" + relativeAddr);
        File dest = new File(PathUtil .getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is:" + PathUtil.getImgBasePath() + relativeAddr);
        try{
            Thumbnails.of(thumbnail).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT,
                            ImageIO.read(new File(basePath + "/watermark.png")), 0.25f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即 /home/o2o/ming/xxx.jpg
     * 要创建 3个 文件夹，home o2o ming
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名
     * 获取最后一个点号的位置，然后获取最后点号后之后的字符串就是自己要的了
     * @param cFile
     * @return
     */
    private static String getFileExtension(File cFile) {
        String originalFileName = cFile.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒 + 五位随机数
     * @return
     */
    private static String  getRandomFileName() {
        // 获取随机的五位数 > 10000 && < 99999
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum; 
    }

    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("/Users/wangxiaoming/Desktop/o2o/src/main/resources/hmbb.jpeg"))
        .size(200, 200)
                .watermark(Positions.BOTTOM_RIGHT,
            ImageIO.read(new File(basePath + "/watermark.png")), 0.25f).outputQuality(0.8f)
        .toFile("/Users/wangxiaoming/Desktop/o2o/src/main/resources/hmbbnew.jpeg");
    }
}

```



###解决问题 --- NullPointerException

描述：error 中只报错 null，没有具体写明哪里是 null；

解决：通过调试终于发现，我把 separator 写错了，这里返回的是空值；

感悟：要细心；参数问题使用 debug 是极为高效的办法，感谢慕课网上的同学启发我用这个方法。

![屏幕快照 2019-09-20 上午11.20.18](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-20 上午11.20.18.png)



###Service层 --- 店铺创建

```java

```

