package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.mapper.QuestionMapper;
import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IQuestionService;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Creaked by EyreValor on 2020/2/28
 */
@Controller
public class PublishController {

    @Autowired
    private IUserService userServiceimpl;

    @Autowired
    private IQuestionService questionServiceimpl;


    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        User user = null;
        user = getUser(request);
        System.err.println(description);
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            return "publish";
        }

        if (title==null||"".equals(title)) {
            model.addAttribute("noTitle","标题不能为空");
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            return "publish";
        }

        if (description==null||"".equals(description)) {
            model.addAttribute("noDescription","问题补充不能为空");
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            return "publish";
        }

        if (tag==null||"".equals(tag)) {
            model.addAttribute("noTag","标签不能为空");
            model.addAttribute("title",title);
            model.addAttribute("description",description);
            model.addAttribute("tag",tag);
            return "publish";
        }

        questionServiceimpl.createQuestion(title,description,tag,user);
        return "redirect:/";
    }


    private User getUser(HttpServletRequest request) {
        User user;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = userServiceimpl.getUserByToken(token);
                    return user;
                }
            }
        }
        return null;
    }
}
