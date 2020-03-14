package cn.glavenus.community.glavenus.advice;

import cn.glavenus.community.glavenus.dto.ResultDTO;
import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creaked by EyreValor on 2020/3/13
 */
//@ControllerAdvice
public class CustomizeExceptionHandler {
    //获取报错信息
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model, HttpServletResponse response) {
        //获取报错数值
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();

        /*
         *判断请求参数是否为application/json 异常返回结果分开处理
         */
        if ("application/json".equals(contentType)) {
            //返回json
            ResultDTO resultDTO = null;
            //判断异常类型
            if (e instanceof CustomizeException) {
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            } else {
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            //直接将值写入response 做法强硬
            try {
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            //判断报错类型
            if (e instanceof CustomizeException) {
                model.addAttribute("message", e.getMessage());
            } else {
                model.addAttribute("message", "服务器过热了！要不等下试试。");
            }
            //返回错误视图
            return new ModelAndView("error");
        }

        return null;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}