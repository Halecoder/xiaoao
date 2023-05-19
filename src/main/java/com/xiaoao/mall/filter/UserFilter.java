package com.xiaoao.mall.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.xiaoao.mall.common.Constant;
import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.exception.XiaoaoMallExceptionEnum;
import com.xiaoao.mall.model.pojo.User;
import com.xiaoao.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述：     用户过滤器
 */
public class UserFilter implements Filter {

    public static User currentUser=new User();




    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException   {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if ("OPTIONS".equals(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = request.getHeader(Constant.JWT_TOKEN);
            if (StringUtils.isEmpty(token)) {
                PrintWriter out = new HttpServletResponseWrapper(
                        (HttpServletResponse) servletResponse).getWriter();
                out.write("{\n"
                        + "    \"status\": 10007,\n"
                        + "    \"msg\": \"NEED_JWT_TOKEN\",\n"
                        + "    \"data\": null\n"
                        + "}");
                out.flush();
                out.close();
                return;
            }

            Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            try {
                DecodedJWT jwt = verifier.verify(token);
                currentUser.setUserId(jwt.getClaim(Constant.USER_ID).asInt());
                currentUser.setUserName(jwt.getClaim(Constant.USER_NAME).asString());
            } catch (TokenExpiredException e) {
                //token过期，抛出异常
                try {
                    throw new XiaoaoMallException(XiaoaoMallExceptionEnum.TOKEN_EXPIRED);
                } catch (XiaoaoMallException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (JWTDecodeException e) {
                //解码失败，抛出异常
                try {
                    throw new XiaoaoMallException(XiaoaoMallExceptionEnum.TOKEN_WRONG);
                } catch (XiaoaoMallException ex) {
                    throw new RuntimeException(ex);
                }
            }


            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}