package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.QuestionDTO;
import cn.glavenus.community.glavenus.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Creaked by EyreValor on 2020/3/8
 */
@Controller
public class QuestionController {
    @Autowired
    IQuestionService questionServiceimpl;

    /**
     * 问题详情
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionServiceimpl.getQuestionById(id);
        model.addAttribute("questionDTO",questionDTO);
        return "question";
    }
}
