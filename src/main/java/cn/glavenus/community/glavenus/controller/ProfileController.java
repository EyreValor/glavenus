package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.PageinationDTO;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IQuestionService;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Creaked by EyreValor on 2020/3/6
 */
@Controller
public class ProfileController {
    @Autowired
    IUserService userServiceImpl;

    @Autowired
    IQuestionService questionServiceImpl;

    /**
     * 个人中心页面
     * @param request
     * @param action
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page) {
        //根据不同请求返回不同页面信息
        if ("question".equals(action)) {
            //返回我的提问页面
            model.addAttribute("section", "question");
            model.addAttribute("sectionName", "我的提问");
            User user = (User) request.getSession().getAttribute("user");
            PageinationDTO pageinationDTO = questionServiceImpl.getQuestions(String.valueOf(user.getId()), page);
            model.addAttribute("pageination",pageinationDTO);
        } else if ("replies".equals(action)) {
            //返回最新回复页面
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
        }

        return "profile";
    }
}
