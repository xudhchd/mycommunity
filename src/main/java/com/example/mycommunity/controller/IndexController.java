package com.example.mycommunity.controller;

import com.example.mycommunity.dto.PageDTO;
import com.example.mycommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String helloString(HttpServletRequest httpServletRequest,
                              Model model,
                              @RequestParam(name = "page",defaultValue = "1") Integer page,
                              @RequestParam(name = "size",defaultValue = "5") Integer size){
        PageDTO pageDTO = questionService.list(page,size);
        model.addAttribute("pageDTO",pageDTO);
        return "index";
    }
}
