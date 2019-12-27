package com.ming.o2o.web.local;

import com.ming.o2o.dto.LocalAuthExecution;
import com.ming.o2o.entity.LocalAuth;
import com.ming.o2o.entity.PersonInfo;
import com.ming.o2o.enums.LocalAuthStateEnum;
import com.ming.o2o.exceptions.LocalAuthOperationException;
import com.ming.o2o.service.LocalAuthService;
import com.ming.o2o.util.CodeUtil;
import com.ming.o2o.util.HttpServletRequestUtil;
import com.ming.o2o.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {
    @Autowired
    private LocalAuthService localAuthService;

    /**
     * 将用户信息与平台绑定
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String ,Object>();
        // 验证码校验
        if(!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取输入的账号
        String username = HttpServletRequestUtil.getString(request, "username");
        // 获取输入的密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 从 session 中获取当前用户信息
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，要求 session 中的数据非空，账号密码非空，（ 注意，session 中的信息来源于微信登录 ）TODO 与微信有关
        if (username != null && password != null && personInfo.getOwnerId() != null) {
            // 创建对象 LocalAuth 并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(username);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(personInfo);
            // 绑定账号
            LocalAuthExecution localAuthExecution = localAuthService.bindLocalAuth(localAuth);
            if(localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", localAuthExecution.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            // TODO 等 wechat 完成后记得修改报错信息
            modelMap.put("errMsg", "用户名密码为空或session信息错误");
        }
        return modelMap;
    };

    /**
     * 修改密码
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 验证码校验
        if(!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取账号
        String userName = HttpServletRequestUtil.getString(request, "username");
        // 获取原密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 获取新密码
        String newPassword = HttpServletRequestUtil.getString(request, "newPassword");
        // 从 session 中获取当前用户信息 TODO 与微信有关
        PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，除了信息、session 非空，还要新旧密码不同
        if (userName != null && password != null && newPassword != null
                && personInfo.getOwnerId() != null && !password.equals(MD5.getMd5(newPassword))) {
            try {
                LocalAuth localAuth = localAuthService.getLocalUserByUserNameAndPwd(userName, password);
                // 查看原先账号，看与输入账号是否相同，不相同就是非法的！
                if (localAuth == null || !localAuth.getUsername().equals(userName)) {
                    // 不一致直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "两次输入的账号不同");
                    return modelMap;
                }
                // 修改平台的账号密码
                LocalAuthExecution localAuthExecution = localAuthService.modifyLocalAuth(personInfo.getOwnerId(), userName,
                        password, newPassword, new Date());
                if (localAuthExecution.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", localAuthExecution.getStateInfo());
                }
            } catch (LocalAuthOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入应填项");
        }
        return modelMap;
    }

    /**
     * 登录校验
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> logincheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        boolean needVerify = HttpServletRequestUtil.getBoolean(request, "needVerify");
        // 验证码校验
        if(needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 获取账号
        String userName = HttpServletRequestUtil.getString(request, "username");
        // 获取密码
        String password = HttpServletRequestUtil.getString(request, "password");
        // 非空校验
        if (userName != null && password != null) {
            // 传入账号和密码去获取平台信息
            LocalAuth localAuth = localAuthService.getLocalUserByUserNameAndPwd(userName, password);
            if (localAuth != null) {
                // 若能取到账号则登录成功
                modelMap.put("success", true);
                // 在 session 中设置账号信息
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "您输入的信息中包含空值");
        }
        return modelMap;
    }

    /**
     * 注销 session
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST )
    @ResponseBody
    private Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 将用户 session 置空
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }
}
