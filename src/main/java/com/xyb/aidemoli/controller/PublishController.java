package com.xyb.aidemoli.controller;

import com.xyb.aidemoli.mapper.QuestionMapper;
import com.xyb.aidemoli.mapper.UserMapper;
import com.xyb.aidemoli.model.Question;
import com.xyb.aidemoli.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam(name="title",required = false)String title,
                            @RequestParam(name="description",required = false)String description,
                            @RequestParam(name="tag",required = false)String tag,
                            HttpServletRequest request,
                            Model model){

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == "" || title == null){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == "" || description == null){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == "" || tag == null){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = Question.builder()
                .title(title)
                .description(description)
                .tag(tag)
                .creator(user.getAccountId())
                .gmtCreate(System.currentTimeMillis())
                .gmtModified(System.currentTimeMillis())
                .likeCount(0)
                .viewCount(0)
                .commentCount(0)
                .build();
        questionMapper.create(question);
        return "redirect:/";
    }
}
