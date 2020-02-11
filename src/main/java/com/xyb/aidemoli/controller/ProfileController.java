package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.dto.PageDTO;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.model.User;
import com.xyb.aidemoli.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name="action")String action,
                          HttpServletRequest request,
                          @RequestParam(name="page",defaultValue="1") Integer page,
                          @RequestParam(name="size",defaultValue="5") Integer size,
                          Model model){

        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }else {
            model.addAttribute("section","repay");
            model.addAttribute("sectionName","最新回复");
        }
        PageDTO pageDTO = questionService.list(user.getAccountId(),page,size);
        model.addAttribute("pagination",pageDTO);
        return "profile";
    }
}
