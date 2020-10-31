package cn.zkz.animal.exception;

public class AniamlException extends RuntimeException {

    /**
     * 异常错误码
     */
    private int code;

    public AniamlException(int code, String args) {
        super(args);
        this.code = code;
    }

    /**
     *
     */
    public AniamlException() {
        super("未知错误");
        this.code = 500;
    }
}
