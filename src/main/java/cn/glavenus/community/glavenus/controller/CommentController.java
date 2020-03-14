package cn.glavenus.community.glavenus.controller;

import cn.glavenus.community.glavenus.dto.CommentDTO;
import cn.glavenus.community.glavenus.dto.ResultDTO;
import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.mapper.CommentMapper;
import cn.glavenus.community.glavenus.model.Comment;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
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
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {

        User user = (User)request.getSession().getAttribute("user");

//        if (user == null){
//            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
//        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setCommentator(1L);// TODO
        comment.setCommentCount(0);
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModefied(comment.getGmtCreate());
        comment.setComment(commentDTO.getComment());
        commentServiceImpl.insert(comment);
        Map<Object,Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("Message","成功");

        return objectObjectHashMap;
    }
}
