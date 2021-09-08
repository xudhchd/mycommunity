package com.example.mycommunity.mapper;

import com.example.mycommunity.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_creat,gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag}) ")
    void create(Question question);
}
