package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.PageinationDTO;
import cn.glavenus.community.glavenus.service.IQuestionService;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Controller
public class IndexController {

    @Autowired
    private IUserService userServiceimpl;

    @Autowired
    private IQuestionService questionServiceImpl;

    /**
     * 浏览首页
     * @param request
     * @param model
     * @param page
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page){
        //获取问题列表
        PageinationDTO pageination= questionServiceImpl.getQuestions(page);
        //将值传递给前端
        model.addAttribute("pageination",pageination);
        return "index";
    }
}
