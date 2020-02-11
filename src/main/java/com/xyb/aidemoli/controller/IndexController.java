package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.dto.PageDTO;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue="1") Integer page,
                        @RequestParam(name="size",defaultValue="5") Integer size) {
        PageDTO pageDTO = questionService.list(page,size);
        model.addAttribute("pagination",pageDTO);
        return "index";
    }
}
