package cn.glavenus.community.glavenus.dto;

import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.exception.CustomizeException;
import lombok.Data;

/**
 * Creaked by EyreValor on 2020/3/14
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;

    public static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }

    public static ResultDTO errorOf(CustomizeException errorCode) {
        return errorOf(errorCode.getCode(), errorCode.getMessage());
    }
}
