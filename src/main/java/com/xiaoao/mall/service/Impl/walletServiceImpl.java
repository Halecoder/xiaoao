package com.xiaoao.mall.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoao.mall.common.Constant;
import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.filter.UserFilter;
import com.xiaoao.mall.model.dao.TransactionMapper;
import com.xiaoao.mall.model.dao.UserMapper;
import com.xiaoao.mall.model.dao.WalletMapper;
import com.xiaoao.mall.model.pojo.PageQuery;
import com.xiaoao.mall.model.pojo.Transaction;
import com.xiaoao.mall.model.pojo.User;
import com.xiaoao.mall.model.pojo.Wallet;
import com.xiaoao.mall.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class walletServiceImpl implements WalletService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    WalletMapper walletMapper;

    @Autowired
    TransactionMapper transactionMapper;



    @Override
    public void addWalletInfo(String bankId) {

        User user = new User();
        user.setUserId(UserFilter.currentUser.getUserId());
        user.setWalletId(IdUtil.simpleUUID());
        user.setBankCardId(bankId);
        userMapper.updateByPrimaryKeySelective(user);
        //拷贝用户信息，生成钱包信息
        Wallet wallet = new Wallet();
        wallet.setWalletId(user.getWalletId());
        wallet.setUserId(user.getUserId());
        wallet.setBalance(BigDecimal.valueOf(0));
        //触发器自动创建钱包
        walletMapper.insertSelective(wallet);

        //继续触发变动表

        Transaction transaction = new Transaction();
        transaction.setTransactionId(IdUtil.simpleUUID());
        transaction.setUserId(user.getUserId());
        transaction.setTransactionType(Constant.WALLET_CHONGZHI);
        transaction.setAmount(BigDecimal.valueOf(0));
        transaction.setTransactionTime(DateUtil.date());
        transactionMapper.insertSelective(transaction);


    }

    @Override
    public boolean checkWalletInfo(String userId, String bankId) throws XiaoaoMallException {

        if(StrUtil.isAllNotEmpty()) {
            return true;
        }

        return false;
    }

    @Override
    public void descWallet(BigDecimal subMoney, String bankId) {

        //根据bankId查询钱包id
        String walletId = walletMapper.selectWalletIdByBankId(bankId);

        //根据钱包id扣除进行消费
        walletMapper.deductAmountFromWallet(walletId, subMoney);

        //记录交易信息
        Transaction transaction = new Transaction();
        transaction.setTransactionId(IdUtil.simpleUUID());
        transaction.setUserId(UserFilter.currentUser.getUserId());
        transaction.setTransactionType(Constant.WALLET_XIAOFEI);
        transaction.setAmount(subMoney);
        transaction.setTransactionTime(DateUtil.date());
        transactionMapper.insertSelective(transaction);


    }

    @Override
    public void addWallet(BigDecimal addMoney, String bankId) {

            //根据bankId查询钱包id
            String walletId = walletMapper.selectWalletIdByBankId(bankId);

            //根据钱包id扣除进行消费
            walletMapper.addAmountToWallet(walletId, addMoney);

            //记录交易信息
            Transaction transaction = new Transaction();
            transaction.setTransactionId(IdUtil.simpleUUID());
            transaction.setUserId(UserFilter.currentUser.getUserId());
            transaction.setTransactionType(Constant.WALLET_CHONGZHI);
            transaction.setAmount(addMoney);
            transaction.setTransactionTime(DateUtil.date());
            transactionMapper.insertSelective(transaction);
    }

    @Override
    public BigDecimal checkWallet(String bankId) {
            //根据bankId查询钱包id
            String walletId = walletMapper.selectWalletIdByBankId(bankId);

            //根据钱包id查询余额
            BigDecimal balance = walletMapper.selectBalanceByWalletId(walletId);

            return balance;
    }

    @Override
    public PageInfo<Transaction> checkWalletChange(PageQuery pageQuery) {

        List<Transaction> transaction = transactionMapper.selectWalletChangeByUserId(UserFilter.currentUser.getUserId());
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        PageInfo pageInfo = new PageInfo<>(transaction);
        pageInfo.setList(transaction);
        return pageInfo;

    }

    @Override
    public boolean checkWalletStatus(Integer userId) {
        if (walletMapper.selectWalletIdByKey(userId) != null) {
            return true;
        } else {
            return false;
        }

    }


}
