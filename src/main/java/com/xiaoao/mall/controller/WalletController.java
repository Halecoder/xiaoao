package com.xiaoao.mall.controller;


import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageInfo;
import com.xiaoao.mall.common.ApiRestResponse;
import com.xiaoao.mall.exception.XiaoaoMallException;
import com.xiaoao.mall.exception.XiaoaoMallExceptionEnum;
import com.xiaoao.mall.filter.UserFilter;
import com.xiaoao.mall.model.pojo.PageQuery;
import com.xiaoao.mall.model.pojo.Transaction;
import com.xiaoao.mall.model.pojo.User;
import com.xiaoao.mall.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * 钱包管理
 * @author: 行者无疆
 */
@RestController
@Tag(name = "WalletController", description = "钱包管理")
@RequestMapping("/wallet")
public class WalletController {


    @Autowired
    WalletService walletService;



    /**
     * 开通我的钱包
     * @param bankId 银行卡号
     * @return
     * @throws XiaoaoMallException
     */
    @PostMapping("/openWallet")
    public ApiRestResponse openWallet(@RequestParam("bankId") String bankId) throws XiaoaoMallException {

        if(StrUtil.isAllNotEmpty(bankId)){
            walletService.addWalletInfo(bankId);
            return ApiRestResponse.success("开通成功");
        }else {
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_CARD_ID);
        }


    }


    /**
     * 查询余额
     * @param bankId 银行卡号
     * @return
     * @throws XiaoaoMallException
     */
    @PostMapping("/checkWallet")
    public ApiRestResponse checkWallet(@RequestParam("bankId") String bankId) throws XiaoaoMallException {


        if(walletService.checkWalletStatus(UserFilter.currentUser.getUserId())){
            if (StrUtil.isAllNotEmpty(bankId)) {
                BigDecimal balance = walletService.checkWallet(bankId);
                return ApiRestResponse.success("您的钱包余额为：" + balance);
            } else {
                return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_CARD_ID);
            }
        }else{
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_OPEN_WALLET);
        }


    }


    /**
     * 变动明细查询
     * @param pageQuery 分页查询
     * @return
     * @throws XiaoaoMallException
     */
    @GetMapping("/checkWalletChange")
    public ApiRestResponse checkWalletChange(PageQuery pageQuery) throws XiaoaoMallException {

        if(walletService.checkWalletStatus(UserFilter.currentUser.getUserId())){

            PageInfo<Transaction> transactions = walletService.checkWalletChange(pageQuery);
            return ApiRestResponse.success(transactions);
        }
        else{
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_OPEN_WALLET);
        }


    }


    /**
     * 消费
     * @param subMoney 扣款金额
     * @param bankId 银行卡号
     * @return
     * @throws XiaoaoMallException
     */
    @PostMapping("/descWallet")
    public ApiRestResponse descWallet(@RequestParam("subMoney") BigDecimal subMoney, @RequestParam("bankId") String bankId) throws XiaoaoMallException {


        if(walletService.checkWalletStatus(UserFilter.currentUser.getUserId())) {
            if (subMoney != null && bankId != null) {
                walletService.descWallet(subMoney, bankId);
                return ApiRestResponse.success("扣款成功");
            } else {
                return ApiRestResponse.error(XiaoaoMallExceptionEnum.SUB_MONEY_FALIED);
            }
        }else{
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_OPEN_WALLET);
        }
    }


    /**
     * 充值或退款
     * @param addMoney 充值金额
     * @param bankId 银行卡号
     * @return
     * @throws XiaoaoMallException
     */
    @PostMapping("/addWallet")
    public ApiRestResponse addWallet(@RequestParam("addMoney") BigDecimal addMoney, @RequestParam("bankId") String bankId) throws XiaoaoMallException {

        if(walletService.checkWalletStatus(UserFilter.currentUser.getUserId())) {

            if (addMoney != null && bankId != null) {
                walletService.addWallet(addMoney, bankId);
                return ApiRestResponse.success("退款成功");
            } else {
                return ApiRestResponse.error(XiaoaoMallExceptionEnum.ADD_MONEY_FALIED);
            }
        }else{
            return ApiRestResponse.error(XiaoaoMallExceptionEnum.NEED_OPEN_WALLET);
        }
    }



}
