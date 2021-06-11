package ball.ingram.demo.exception;

public class CustomizeException extends RuntimeException {
    private String messsage;
    private Integer code;

    @Override
    public String getMessage() {
        return messsage;
    }

    public Integer getCode() {
        return code;
    }


    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.messsage = errorCode.getMessage();
    }
}

