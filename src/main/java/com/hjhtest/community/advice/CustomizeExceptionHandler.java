package com.hjhtest.community.advice;

import com.alibaba.fastjson.JSON;
import com.hjhtest.community.data_transfer_object.ResultDTO;
import com.hjhtest.community.exception.CustomizeErrorCode;
import com.hjhtest.community.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @ClassName: CustomizeExceptionHandler
 * @Description:
 * @author: hjh
 * @date: 2022/2/26 10:41
 */
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request,
                  HttpServletResponse response,
                  Throwable ex, Model model) {

        String contentType = request.getContentType();
        if("application/json".equals(contentType)){
            //返回JSON
            ResultDTO resultDTO;

            if(ex instanceof CustomizeException){
                resultDTO = (ResultDTO) ResultDTO.errorOf( (CustomizeException)ex);
            }else {
                resultDTO =  ResultDTO.errorOf(CustomizeErrorCode.SYSTEM_ERROR);
            }

            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ioe) {
            }
            return null;

        }
        else {
            //错误页面跳转
            if(ex instanceof CustomizeException){
                model.addAttribute("message",ex.getMessage());
            }else {
                model.addAttribute("message",CustomizeErrorCode.SYSTEM_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }



    }



}
