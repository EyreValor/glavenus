package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.CommentDTO;
import cn.glavenus.community.glavenus.dto.QuestionDTO;
import cn.glavenus.community.glavenus.enums.CommentTypeEnum;
import cn.glavenus.community.glavenus.service.ICommentService;
import cn.glavenus.community.glavenus.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Creaked by EyreValor on 2020/3/8
 */
@Controller
public class QuestionController {
    @Autowired
    IQuestionService questionServiceimpl;
    @Autowired
    ICommentService commentServiceImpl;
    /**
     * 问题详情
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model) {
        QuestionDTO questionDTO = questionServiceimpl.getQuestionById(id);
        List<QuestionDTO> relatedQuestion = questionServiceimpl.getRelatedQuestion(questionDTO);
        List<CommentDTO> comments = commentServiceImpl.ListByQuestionId(id, CommentTypeEnum.QUESTION);

        //增加阅读数
        questionServiceimpl.incView(id);
        model.addAttribute("questionDTO", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestion",relatedQuestion);
        return "question";
    }
}