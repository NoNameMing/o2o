package com.ming.o2o.service;

import com.ming.o2o.dto.LocalAuthExecution;
import com.ming.o2o.entity.LocalAuth;
import com.ming.o2o.exceptions.LocalAuthOperationException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthService {
    /**
     * 通过账号和密码获取平台账号信息
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth getLocalUserByUserNameAndPwd(String username, String password);

    /**
     * 通过 userId 获取用户信息
     *
     * @param userId
     * @return
     */
    LocalAuth getLocalByUserId(long userId);

    /**
     * 绑定微信，生成专属的平台账号
     *
     * @param localAuth
     * @return
     * @throws LocalAuthOperationException
     */
    LocalAuthExecution bindLocalAuth(LocalAuth localAuth) throws LocalAuthOperationException;

    /**
     * 修改平台的账号密码
     *
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    LocalAuthExecution modifyLocalAuth(Long userId, String username, String password,
                                       String newPassword, Date lastEditTime) throws LocalAuthOperationException;
}
