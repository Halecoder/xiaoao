package com.xiaoao.mall.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.xiaoao.mall.common.ApiRestResponse;
import com.xiaoao.mall.common.Constant;
import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.exception.XiaoaoMallExceptionEnum;
import com.xiaoao.mall.model.pojo.User;
import com.xiaoao.mall.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * 用户管理
 * @author: 行者无疆
 */
@RestController
@RequestMapping("/user")
@Tag(name = "UserController", description = "用户管理")
public class UserController {

    @Autowired
    UserService userService;


    /**
     * JWT登录
     * @param userName 用户名
     * @param password 密码
     * @return
     * @throws XiaoaoMallException
     */
    @GetMapping("/loginWithJwt")
    public ApiRestResponse loginWithJwt(@RequestParam("username") String userName, @RequestParam String password) throws XiaoaoMallException {
        if (StringUtils.isEmpty(userName)) {
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        //保存用户信息时，不保存密码
        user.setPassword(null);
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        String token = JWT.create()
                .withClaim(Constant.USER_NAME, user.getUserName())
                .withClaim(Constant.USER_ID, user.getUserId())
                //过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + Constant.EXPIRE_TIME))
                .sign(algorithm);
        return ApiRestResponse.success(token);
    }


    /**
     * 用户注册
     * @param userName 用户名
     * @param password 密码
     * @return
     * @throws XiaoaoMallException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String userName,@RequestParam("password") String password) throws XiaoaoMallException {
        if (StringUtils.isEmpty(userName)) {//如果用户名为空，直接返回用户名不能为空的信息；
            return  ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {//如果密码为空，直接返回密码不能为空的信息；
            return  ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 8) {//如果密码长度小于8位，直接返回密码长度不能小于8位的信息；
            return  ApiRestResponse.error(XiaoaoMallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName, password);
        return ApiRestResponse.success();
    }


    /**
     * 传统登录
     * @param username 用户名
     * @param password 密码
     * @param session 会话
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String username,  @RequestParam("password") String  password,  HttpSession session) throws XiaoaoMallException {
        if (StringUtils.isEmpty(username)) {//如果用户名为空，直接返回用户名不能为空的信息；
            return  ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)) {//如果密码为空，直接返回密码不能为空的信息；
            return  ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(username, password);
        user.setPassword(null);//返回用户信息时候，去掉密码
        session.setAttribute(Constant.XIAOAO_MALL_USER, user);
        return ApiRestResponse.success(user);
    }



    /**
     *退出登录
     * @param session 会话
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.XIAOAO_MALL_USER);
        return ApiRestResponse.success();
    }
}
