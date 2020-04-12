package cn.glavenus.community.glavenus.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    /**
     * 定义错误类型常量
     */
    QUESTION_NOT_FOUND(2001, "你找的问题被吃掉了。"),
    CHANGE_QUESTION_NOT_FOUND(2002, "你修改的问题不存在。"),
    TARGET_PAREM_NOT_FOUND(2003, "未选中任何一个问题或评论回复。"),
    TARGET_QUESTION_NOT_FOUND(2004, "该提问已被删除。"),
    TARGET_COMMENT_NOT_FOUND(2005, "该回复已被删除。"),
    TARGET_TYPE_NOT_FOUND(2006, "回复参数异常。"),
    COMMENT_IS_EMPTY(2007, "输入内容不能为空。"),
    NO_LOGIN(3000, "当前操作需要登录，请登录后重试。"),
    SYS_ERROR(5000, "服务器过热了！要不等下试试。"),
    WRITE_UNKNOWN_ERROR(5001, "写入时出现未知错误。"),
    ILLEGAL_PARAMETER(5002, "非法参数。"),
    USER_NOT_FOUND(6000, "用户不存在。"),
    ;

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
