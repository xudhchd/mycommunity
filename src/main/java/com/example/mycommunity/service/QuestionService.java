package com.example.mycommunity.service;

import com.example.mycommunity.dto.PageDTO;
import com.example.mycommunity.dto.QuestionDTO;
import com.example.mycommunity.mapper.QuestionMapper;
import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.model.Question;
import com.example.mycommunity.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PageDTO list(Integer page, Integer size) {
        Integer countlines = questionMapper.countlines();
        Integer totalPage;
        if (countlines % size == 0) {
            totalPage = countlines / size;
        } else {
            totalPage = countlines / size + 1;
        }
        if (page < 1) {
            page=1;
        } else if (page > totalPage) {
            page = totalPage;
        }
        Integer offset =(page - 1)*size;
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        PageDTO pageDTO = new PageDTO();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOS);

        pageDTO.setPagePara(totalPage, page, size);
        return pageDTO;
    }
}
