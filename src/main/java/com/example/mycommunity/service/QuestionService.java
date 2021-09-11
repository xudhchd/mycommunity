package com.example.mycommunity.service;

import com.example.mycommunity.dto.PageDTO;
import com.example.mycommunity.dto.QuestionDTO;
import com.example.mycommunity.exception.CustomizeErrorCode;
import com.example.mycommunity.exception.CustomizeException;
import com.example.mycommunity.mapper.QuestionMapper;
import com.example.mycommunity.mapper.UserMapper;
import com.example.mycommunity.model.Question;
import com.example.mycommunity.model.QuestionExample;
import com.example.mycommunity.model.User;
import org.apache.ibatis.session.RowBounds;
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
        Integer countlines = (int)questionMapper.countByExample(new QuestionExample());
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
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOS = new ArrayList<>();


        PageDTO pageDTO = new PageDTO();
        for (Question question : questions) {

            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOS);

        pageDTO.setPagePara(totalPage, page, size);
        return pageDTO;
    }

    public PageDTO listById(Integer id, Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(id);
        Integer countlines = (int)questionMapper.countByExample(new QuestionExample());

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

        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(id);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOS = new ArrayList<>();


        PageDTO pageDTO = new PageDTO();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);
        }
        pageDTO.setQuestionDTOList(questionDTOS);

        pageDTO.setPagePara(totalPage, page, size);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            question.setGmtCreat(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreat());
            questionMapper.insert(question);
        } else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }

    }
}
