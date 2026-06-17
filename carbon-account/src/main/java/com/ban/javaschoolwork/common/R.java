package com.ban.javaschoolwork.common;


import com.ban.javaschoolwork.enums.ResultEnum;
import lombok.Data;

@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> ok() {
        return result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), null);
    }

    public static <T> R<T> ok(T data) {
        return result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    public static <T> R<T> error() {
        return result(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg(), null);
    }

    public static <T> R<T> error(String msg) {
        return result(ResultEnum.FAIL.getCode(), msg, null);
    }

    public static <T> R<T> result(Integer code, String msg, T data) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
