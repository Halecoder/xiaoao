package com.xiaoao.mall.model.dao;

import com.xiaoao.mall.model.pojo.PageQuery;
import com.xiaoao.mall.model.pojo.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionMapper {
    int deleteByPrimaryKey(String transactionId);

    int insert(Transaction record);

    int insertSelective(Transaction record);

    Transaction selectByPrimaryKey(String transactionId);

    int updateByPrimaryKeySelective(Transaction record);

    int updateByPrimaryKey(Transaction record);

    List<Transaction> selectWalletChangeByUserId(Integer userId);
}