package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.enums.CommentTypeEnum;
import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.exception.CustomizeException;
import cn.glavenus.community.glavenus.mapper.CommentMapper;
import cn.glavenus.community.glavenus.mapper.QuestionExtMapper;
import cn.glavenus.community.glavenus.mapper.QuestionMapper;
import cn.glavenus.community.glavenus.model.Comment;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creaked by EyreValor on 2020/3/14
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Override
    public void insert(Comment comment) {

        //TODO

        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PAREM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            //TODO
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                //TODO
            }
            commentMapper.insert(comment);
        } else {

            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                //TODO
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incComment(question);
        }

    }
}
