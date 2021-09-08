package com.example.mycommunity.controller;

import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/")
    public String helloString(HttpServletRequest httpServletRequest){
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null){
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

        return "index";
    }
}
