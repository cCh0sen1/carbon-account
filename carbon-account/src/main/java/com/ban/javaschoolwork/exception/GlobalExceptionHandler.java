package com.ban.carbonaccount.exception;

import com.ban.carbonaccount.common.R;
import com.ban.carbonaccount.enums.ResultEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public R<?> bizException(BizException e) {
        return R.result(e.getCode(), e.getMsg(), null);
    }

    @ExceptionHandler(BindException.class)
    public R<?> paramException(BindException e) {
        String msg = e.getFieldError().getDefaultMessage();
        return R.result(ResultEnum.PARAM_ERROR.getCode(), msg, null);
    }

    @ExceptionHandler(Exception.class)
    public R<?> allException(Exception e) {
        e.printStackTrace();
        return R.error("系统繁忙，请稍后重试");
    }
}

