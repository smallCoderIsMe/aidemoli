package com.xyb.aidemoli.service;

import com.xyb.aidemoli.dto.PageDTO;
import com.xyb.aidemoli.dto.QuestionDTO;
import com.xyb.aidemoli.mapper.QuestionMapper;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.model.Question;
import com.xyb.aidemoli.model.User;
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
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.count();
        pageDTO.setPagination(totalCount,page,size);
        //防止出现页面为负数，或者大于总页数的情况
        if(page < 1 )
            page = 1;
        if(page > pageDTO.getTotalPage())
            page = pageDTO.getTotalPage();
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public PageDTO list(String accountId, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer totalCount = questionMapper.countByAccountId(accountId);
        pageDTO.setPagination(totalCount,page,size);
        //防止出现页面为负数，或者大于总页数的情况
        if(page < 1 )
            page = 1;
        if(page > pageDTO.getTotalPage())
            page = pageDTO.getTotalPage();
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.listByAccountId(accountId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findByAccountId(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user =  userMapper.findByAccountId(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }
}
