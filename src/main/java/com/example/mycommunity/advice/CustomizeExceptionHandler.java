package com.example.mycommunity.advice;

import com.alibaba.fastjson.JSON;
import com.example.mycommunity.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        if (e instanceof CustomizeException) {
            model.addAttribute("message", e.getMessage());
        } else {
            model.addAttribute("message", "服务冒烟了，稍后再试一下");
        }
        return new ModelAndView("error");
    }
}
