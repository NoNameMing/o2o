package com.ming.o2o.service.impl;

import com.ming.o2o.dao.LocalAuthDao;
import com.ming.o2o.dto.LocalAuthExecution;
import com.ming.o2o.entity.LocalAuth;
import com.ming.o2o.enums.LocalAuthStateEnum;
import com.ming.o2o.exceptions.LocalAuthOperationException;
import com.ming.o2o.service.LocalAuthService;
import com.ming.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {
    @Autowired
    private LocalAuthDao localAuthDao;

    @Override
    public LocalAuth getLocalUserByUserNameAndPwd(String username, String password) {
        return localAuthDao.queryLocalUserByUserNameAndPwd(username, MD5.getMd5(password));
    }

    @Override
    public LocalAuth getLocalByUserId(long userId) {
        return localAuthDao.queryLocalByUserId(userId);
    }

    @Override
    @Transactional
    public LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException {
        // 空值判断
        if(localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
                || localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getOwnerId() == null) {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
        // 查询此用户是否已绑定过平台账号
        LocalAuth tempAuth = localAuthDao.queryLocalByUserId(localAuth.getPersonInfo().getOwnerId());
        if(tempAuth != null) {
            // 如果绑定过则直接退出，以保证平台账号的唯一性
            return new LocalAuthExecution(LocalAuthStateEnum.ONLY_ONE_ACCOUNT);
        }
        try {
            // 如果没有绑定过，则创建一个平台账号与之绑定
            localAuth.setCreateTime(new Date());
            localAuth.setLastEditTime(new Date());
            // 对密码进行 MD5 加密
            localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
            int effectedNum = localAuthDao.insertLocalAuth(localAuth);
            // 判断是否创建成功
            if(effectedNum <= 0) {
                throw new LocalAuthOperationException("账号绑定失败");
            } else {
                return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
            }
        } catch (Exception e) {
            throw new LocalAuthOperationException("insertLocalAuth error:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword, Date lastEditTime) throws LocalAuthOperationException {
        // 非空判断
        if(userId != null && username != null && password != null && newPassword != null
            && !password.equals(newPassword)) {
            try {
                // 更新密码，并对新密码进行 MD5 加密
                int effectedNum = localAuthDao.updateLocalAuth(userId, username, MD5.getMd5(password),
                        MD5.getMd5(newPassword), new Date());
                // 判断是否更新成功
                if (effectedNum <= 0) {
                    throw new LocalAuthOperationException("更新失败");
                }
                 return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
            } catch (Exception e) {
                throw new LocalAuthOperationException("更新密码失败：" + e.getMessage());
            }
        } else {
            return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
        }
    }
}
