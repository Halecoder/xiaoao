package com.xiaoao.mall.service;

import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.model.pojo.User;
import org.springframework.stereotype.Service;


public interface UserService {

    void register(String userName, String password) throws XiaoaoMallException;


    User login(String userName, String password) throws XiaoaoMallException;
}
