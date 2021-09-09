package com.example.mycommunity.controller;

import com.example.mycommunity.dto.PageDTO;
import com.example.mycommunity.dto.QuestionDTO;
import com.example.mycommunity.mapper.QuestionMapper;
import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.model.Question;
import com.example.mycommunity.model.User;
import com.example.mycommunity.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;


    @GetMapping("/")
    public String helloString(HttpServletRequest httpServletRequest,
                              Model model,
                              @RequestParam(name = "page",defaultValue = "1") Integer page,
                              @RequestParam(name = "size",defaultValue = "5") Integer size){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null){
                        httpServletRequest.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        PageDTO pageDTO = questionService.list(page,size);
        model.addAttribute("pageDTO",pageDTO);
        return "index";
    }
}
