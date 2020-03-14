package cn.glavenus.community.glavenus.enums;

public enum CommentTypeEnum {
    //回复类型枚举

    QUESTION(1),
    COMMENT(2);

    private Integer type;

    //判断客户端闯入的问题类型是否合法
    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
            if (value.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    CommentTypeEnum(Integer type) {
        this.type = type;
    }
}
