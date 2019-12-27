package com.ming.o2o.service;

import com.ming.o2o.BaseTest;
import com.ming.o2o.dto.LocalAuthExecution;
import com.ming.o2o.entity.LocalAuth;
import com.ming.o2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthServiceTest extends BaseTest {
    @Autowired
    private LocalAuthService localAuthService;

    @Test
    public void testABindLocalAuth() {
        // 新增一条平台账号
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        String username = "testusername";
        String password = "testNpassword";
        // 给平台账号设置上用户信息
        // 给用户设置上用户 Id，标明是哪个用户创建的账号
        personInfo.setOwnerId(1L);
        // 给平台账号设置用户信息，标明是与哪个用户绑定
        localAuth.setPersonInfo(personInfo);
        // 设置账号
        localAuth.setUsername(username);
        // 设置密码
        localAuth.setPassword(password);
        // 绑定账号
        LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
        // TODO 通过与微信账号对比查看是否正确
        // 通过 Id 查询新增的 localAuth
        localAuth = localAuthService.getLocalByUserId(personInfo.getOwnerId());
        // 打印用户名和密码看看跟预期是否相同
        System.out.println("用户昵称：" + localAuth.getPersonInfo().getName());
        System.out.println("用户密码：" + localAuth.getPassword());
    }

    @Test
    public void testBModifyLocalAuth() {
        // 设置账号信息
        long userId = 1;
        String username = "testusername";
        String password = "testNpassword";
        String newPassword = "testmodifypassword";
        // 修改该账号对应的密码
        LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(userId, username,
                password, newPassword, new Date());
        // 通过账号密码找到修改后的 localAuth
        LocalAuth localAuth = localAuthService.getLocalUserByUserNameAndPwd(username, newPassword);
        // 打印用户名判断是否与预期相等
        System.out.println(localAuth.getPersonInfo().getName());
    }
}
