package cn.glavenus.community.glavenus.exception;

/**
 * Creaked by EyreValor on 2020/3/13
 */
public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    //在throw new 异常时设置错误类型
    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
