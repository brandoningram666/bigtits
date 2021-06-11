package ball.ingram.demo.enums;

import ball.ingram.demo.mapper.CommentMapper;

public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private Integer type;

    public static boolean isExit(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType() == type){
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
