package ball.ingram.demo.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

        QUESTION_NOT_FOUND(2001,"你哥这找屁呢 没有这个问题"),
        TARGET_NOT_FOND(2002,"未选中问题或评论"),
        NOT_LOGIN(2003,"未登录，请先登录"),
        SYSTEM_ERROR(2004,"请检查自己今天出门有没有上香"),
        TYPE_PARAM_WRONG(2005,"类型错误"),
        COMMENT_NOT_FONUD(2006,"评论找不到"),
    CONTENT_IS_EMPTY(2007,"回复不能为空"),
    NOTIFICATION_NOT_FOUND(2008,"未读到通知"),
   READ_NOTIFICATION_FAIL(2009,"读到的不是自己的通知"),
    FILE_NOT_FOUND(2010,"文件找不到");
        private String message;
        private Integer code;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
