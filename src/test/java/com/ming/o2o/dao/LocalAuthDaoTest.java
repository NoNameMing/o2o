package com.ming.o2o.dao;

import com.ming.o2o.BaseTest;
import com.ming.o2o.entity.LocalAuth;
import com.ming.o2o.entity.PersonInfo;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LocalAuthDaoTest extends BaseTest {
    @Autowired
    private LocalAuthDao localAuthDao;
    private static final String username = "testusername";
    private static final String password = "testpassword";

    @Test
    public void testAInsertLocalAuth() throws Exception {
        // 新增一条平台账号信息
        LocalAuth localAuth = new LocalAuth();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setOwnerId(1L);
        // 给平台账号绑定上用户信息
        localAuth.setPersonInfo(personInfo);
        // 设置上用户名和密码
        localAuth.setUsername(username);
        localAuth.setPassword(password);
        localAuth.setCreateTime(new Date());
        int effectedNum = localAuthDao.insertLocalAuth(localAuth);
        assertEquals(1, effectedNum);
    }

    @Test
    public void testBQueryLocalByUserNameAndPwd() throws Exception {
        // 按照账号密码查询用户信息
        LocalAuth localAuth = localAuthDao.queryLocalUserByUserNameAndPwd(username, password);
        assertEquals("测试", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testCQueryLocalByUserId() throws Exception {
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        assertEquals("测试", localAuth.getPersonInfo().getName());
    }

    @Test
    public void testDUpdateLocalAuth() throws Exception {
        // 依据用户 Id，以及旧密码修改平台账号密码
        Date now = new Date();
        int effectedNum = localAuthDao.updateLocalAuth(1L, username, password, "newPassword",now);
        assertEquals(1, effectedNum);
        // 查询出该条平台账号的最新消息
        LocalAuth localAuth = localAuthDao.queryLocalByUserId(1L);
        // 输出新密码
        System.out.println(localAuth.getPassword());
    }
}
