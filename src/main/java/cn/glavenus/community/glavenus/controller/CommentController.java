package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.CommentCreateDTO;
import cn.glavenus.community.glavenus.dto.CommentDTO;
import cn.glavenus.community.glavenus.dto.ResultDTO;
import cn.glavenus.community.glavenus.enums.CommentTypeEnum;
import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.mapper.CommentMapper;
import cn.glavenus.community.glavenus.model.Comment;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.ICommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Creaked by EyreValor on 2020/3/14
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ICommentService commentServiceImpl;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute("user");

        //未登录异常
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        //参数为空异常
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getComment())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
        }

        //封装回复数据
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setCommentator(1L);// TODO
        comment.setCommentCount(0);
        comment.setLikeCount(0L);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModefied(comment.getGmtCreate());
        comment.setComment(commentCreateDTO.getComment());

        //写入数据库
        commentServiceImpl.insert(comment);

        Map<Object, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("Message", "成功");
        objectHashMap.put("code", 200);
        return objectHashMap;
    }

    /**
     * 获取评论信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentServiceImpl.ListByQuestionId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }

}
