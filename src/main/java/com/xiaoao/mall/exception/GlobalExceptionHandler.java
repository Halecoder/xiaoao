
package com.xiaoao.mall.exception;

import com.xiaoao.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 描述：  处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception",e);
        return  ApiRestResponse.error(XiaoaoMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(XiaoaoMallException.class)
    @ResponseBody
    public Object handleImoocMallException(XiaoaoMallException e) {
        log.error("XiaoaoPostException",e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }
}
  