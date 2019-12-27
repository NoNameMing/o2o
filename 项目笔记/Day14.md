##前后联调测试

####ajax 报错 Uncaught TypeError

**报错截图**：

![屏幕快照 2019-09-27 上午9.12.47](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 上午9.12.47.png)

**问题**：我不太明白这个问题出处。看视频老师也遇到了一样的报错，他打错了一个类型。导致报这个错误。他把processData 打成了 proceesData，而我把 processData 打成了 processType。

**解决**：更改 ajax 中的内容即可。

![屏幕快照 2019-09-27 上午9.21.28](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 上午9.21.28.png)



#### ajax 报错 Uncaught TypeError

**问题**：这里是 data.success 不是一个功能。把判定语句中相关部分改成如下内容即可。

![屏幕快照 2019-09-27 上午9.17.43](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 上午9.17.43.png)

**解决**：

```js
success: function (data) {
                    if (data.success) {
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！' + data.errMsg);
                    }
                    $('#captcha_img').click();
                }
```



####验证码输入正确却报错问题

**问题**：输入的验证码完全正确，但是总提示验证码输入错误。

**分析**：通过 DEBUG 可以发现，前端正常获取了实际输入的验证码 verifyCodeActual 的值，但是后端这个值就是空值 null。

**解决**：通过进一步看视频发现，老师还没有引入配置和相关的 jar 包。引入之后即可解决。

记录一下引入的包如下：

- commons-fileupload

```xml
    <!-- https://mvnrepository.com/artifact/commons-fileupload/commons-fileupload -->
    <dependency>
      <groupId>commons-fileupload</groupId>
      <artifactId>commons-fileupload</artifactId>
      <version>1.3.3</version>
    </dependency>
```

- spring-web.xml

```xml
    <!-- 文件上传解析器 -->
    <bean id = "multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver ">
        <property name="defaultEncoding" value="utf-8"></property>
        <!-- 1024 * 1024 * 20 = 20M-->
        <property name="maxUploadSize" value="20971520"></property>
        <property name="maxInMemorySize"  value="20971520"></property>
    </bean>
```



####testModifyShop 

**原因**：功能可以实现，但无法返回图片地址。

**报错**：

![屏幕快照 2019-09-27 下午4.52.59](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 下午4.52.59.png)

**分析**：再次 DEBUG，发现 shopExecution 有值，但是获取不了信息。这应是 modifyShop 中没有获取信息；

![屏幕快照 2019-09-27 下午5.01.22](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 下午5.01.22.png)

**解决**：在这行，加入返回 shop。

![屏幕快照 2019-09-27 下午4.53.31](/Users/wangxiaoming/Desktop/屏幕快照 2019-09-27 下午4.53.31.png)



##店铺创建实现逻辑

前端获取用户输入的信息，最后看验证码是否正确；若正确，就把输入的字符串传给后端，后端通过 mapper 将信息实体化为一个类；接下来要处理获取的图片，这个方法以前已经实现过了；最后要做的就是注册这个店铺。



**考试**：网络、数据库、综合编程题

**面试**：项目、技术