package com.ban.carbonaccount.exception;

import com.ban.carbonaccount.enums.ResultEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {
    private Integer code;
    private String msg;

    public BizException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    public BizException(String msg) {
        super(msg);
        this.code = 500;
        this.msg = msg;
    }
}