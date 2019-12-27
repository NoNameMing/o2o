package com.ming.o2o.dao;

import com.ming.o2o.entity.LocalAuth;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LocalAuthDao {
    /**
     * 通过账号和密码查询对应信息，登录用
     *
     * @param username
     * @param password
     * @return
     */
    LocalAuth queryLocalUserByUserNameAndPwd(@Param("username")String username, @Param("password")String password);

    /**
     * 通过用户 Id 查询对应用户
     *
     * @param userId
     * @return
     */
    LocalAuth queryLocalByUserId(@Param("userId")long userId);

    /**
     * 添加平台账号
     *
     * @param localAuth
     * @return
     */
    int insertLocalAuth(LocalAuth localAuth);

    /**
     * 通过 userId，username，password 更改密码
     *
     * @param userId
     * @param username
     * @param password
     * @param newPassword
     * @param lastEditTime
     * @return
     */
    int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
                        @Param("password") String password, @Param("newPassword") String newPassword,
                        @Param("lastEditTime") Date lastEditTime);
}
