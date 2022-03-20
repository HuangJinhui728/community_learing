package com.hjhtest.community.data_transfer_object;

import com.hjhtest.community.exception.CustomizeErrorCode;
import com.hjhtest.community.exception.CustomizeException;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: ResultDTO
 * @Description:
 * @author: hjh
 * @date: 2022/3/18 19:47
 */
@Data
public class ResultDTO<T> {
    int code;
    private String message;
    private T data;


    public static  ResultDTO errorOf(Integer code, String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
        return errorOf(errorCode.getCode(),errorCode.getMessage());
    }

    public static Object errorOf(CustomizeException ex) {
        return errorOf(ex.getCode(), ex.getMessage());
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static <T> ResultDTO okOf(T t){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }

}
