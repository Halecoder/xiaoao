package com.xiaoao.mall.service.Impl;

import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.exception.XiaoaoMallExceptionEnum;
import com.xiaoao.mall.model.dao.UserMapper;
import com.xiaoao.mall.model.pojo.User;
import com.xiaoao.mall.service.UserService;
import com.xiaoao.mall.utils.MD5Utils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class userServiceImpl implements UserService {

        @Autowired
         UserMapper userMapper;

        @Override
        public void register(String userName, String password) throws XiaoaoMallException{
            // TODO Auto-generated method stub

                //首先，查询用户名是否存在；注册时，不允许重名；
//                User result = userMapper.selectByName(userName);
//                if (result != null) {
//                        //如果result不为null，这说明已经有这个用户了；此时，就可以直接向上抛异常；
//                        throw new  XiaoaoMallException(XiaoaoMallExceptionEnum.NAME_EXISTED);
//                }


                //如果上面没有抛异常，那么说明用户名没有重名，那么我们就可以把这个注册信息写到数据库中
                User user = new User();
                user.setUserName(userName);
                //user.setPassword(password);
                try{
                        user.setPassword(MD5Utils.getMD5String(password));
                }catch(NoSuchAlgorithmException e){
                        e.printStackTrace();
                }

                int count = userMapper.insertSelective(user);
                if (count == 0) {
                        throw new XiaoaoMallException(XiaoaoMallExceptionEnum.INSERT_FAILED);
                }


        }

        @Override
        public User login(String userName, String password) throws XiaoaoMallException {
                String md5Password = null;
                try {
                        md5Password = MD5Utils.getMD5String(password);
                } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                }
                User user = userMapper.selectLogin(userName, md5Password);
                if (user == null) {
                        throw new  XiaoaoMallException(XiaoaoMallExceptionEnum.WRONG_PASSWORD);
                }
                return user;
        }

}
