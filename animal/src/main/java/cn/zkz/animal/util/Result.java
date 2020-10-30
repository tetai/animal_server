package cn.zkz.animal.util;



import org.apache.commons.lang.StringUtils;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {

    private  final String MESSAGE_SUCCESS_VUALE = "OK";
    private  final String MESSAGE_SUCCESS_KEY = "message";
    private  final int CODE_SUCCESS_VUALE = 200;
    private  final String CODE_SUCCESS_KEY = "code";

    private Result() {

        this.put(MESSAGE_SUCCESS_KEY, MESSAGE_SUCCESS_VUALE);
        this.put(CODE_SUCCESS_KEY, CODE_SUCCESS_VUALE);
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    private Result(String msg, Integer code) {
        if (StringUtils.isEmpty(msg)) {
            this.put(MESSAGE_SUCCESS_KEY, MESSAGE_SUCCESS_VUALE);
        }else {
            this.put(MESSAGE_SUCCESS_KEY, msg);
        }
        if (code == null) {
            this.put(CODE_SUCCESS_KEY, CODE_SUCCESS_VUALE);
        }else {
            this.put(CODE_SUCCESS_KEY, code);
        }

    }

    public static Result success() {
        return new Result();
    }

    public static Result success(String msg) {
        return new Result(msg, null);
    }

    public static Result error() {

        return new Result(null, -1);
    }
    public static Result error(String msg) {

        return new Result(msg, -1);
    }

    public static Result error(Integer code) {

        return new Result(null, code);
    }
    public static Result error(String msg, Integer code) {

        return new Result(msg, code);
    }

}
