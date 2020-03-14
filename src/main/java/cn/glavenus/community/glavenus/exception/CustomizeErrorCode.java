package cn.glavenus.community.glavenus.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    /**
     * 定义错误类型常量
     */
    QUESTION_NOT_FOUND(2001, "你找的问题被吃掉了。"),
    TARGET_PAREM_NOT_FOUND(2002, "未选中任何一个问题或评论回复。"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试。"),
    SYS_ERROR(5000, "服务器过热了！要不等下试试。");

    //定义枚举类型
    private Integer code;
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
