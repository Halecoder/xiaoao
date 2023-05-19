package com.xiaoao.mall.model.dao;

import com.xiaoao.mall.model.pojo.Wallet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface WalletMapper {
    int deleteByPrimaryKey(String walletId);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(String walletId);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);

    String selectWalletIdByBankId(String bankId);

    int deductAmountFromWallet(String walletId, @Param("amount") BigDecimal subMoney);

    int addAmountToWallet(String walletId, @Param("amount") BigDecimal addMoney);

    BigDecimal selectBalanceByWalletId(String walletId);

    String selectWalletIdByKey(Integer userId);
}