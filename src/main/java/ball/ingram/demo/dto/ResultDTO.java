package ball.ingram.demo.dto;

import ball.ingram.demo.exception.CustomizeErrorCode;
import ball.ingram.demo.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDTO <T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;

    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
       return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(),ex.getMessage());
    }
    public static ResultDTO okOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode(200);
        return resultDTO;
    }
    public static <T> ResultDTO okOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setMessage("请求成功");
        resultDTO.setCode(200);
        resultDTO.setData(t);
        return resultDTO;
    }


}
