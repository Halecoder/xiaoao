package com.xiaoao.mall.service;

import com.github.pagehelper.PageInfo;
import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.model.pojo.PageQuery;
import com.xiaoao.mall.model.pojo.Transaction;
import com.xiaoao.mall.model.pojo.User;

import java.math.BigDecimal;

public interface WalletService {


    void addWalletInfo(String bankId);


    boolean checkWalletInfo(String userId, String bankId) throws XiaoaoMallException;


    void descWallet(BigDecimal subMoney, String bankId);

    void addWallet(BigDecimal addMoney, String bankId);

    BigDecimal checkWallet(String bankId);

    PageInfo<Transaction> checkWalletChange(PageQuery pageQuery);

    boolean checkWalletStatus(Integer userId);
}
