package com.example.mycommunity.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id,
                       Model model) {
        QuestionDTO questionMapperById = questionService.getById(id);
        model.addAttribute("title", questionMapperById.getTitle());
        model.addAttribute("description", questionMapperById.getDescription());
        model.addAttribute("tag", questionMapperById.getTag());
        model.addAttribute("id", questionMapperById.getId());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest httpServletRequest,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "至少添加一个标签");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        User user = (User) httpServletRequest.getSession().getAttribute("user");

        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        question.setCreator(user.getId());
        question.setGmtCreat(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreat());
        question.setId(id);
        questionService.createOrUpdate(question);
        //questionMapper.create(question);
        return "redirect:/";
    }

}
