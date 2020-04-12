package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.dto.CommentDTO;
import cn.glavenus.community.glavenus.enums.CommentTypeEnum;
import cn.glavenus.community.glavenus.exception.CustomizeErrorCode;
import cn.glavenus.community.glavenus.exception.CustomizeException;
import cn.glavenus.community.glavenus.mapper.*;
import cn.glavenus.community.glavenus.model.*;
import cn.glavenus.community.glavenus.service.ICommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Creaked by EyreValor on 2020/3/14
 */
@Service
public class CommentServiceImpl implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public void insert(Comment comment) {
        //未指定父类id异常
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PAREM_NOT_FOUND);
        }
        //回复类型参数异常
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_TYPE_NOT_FOUND);
        }
        //判断回复类型
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            //提问不存在异常
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.TARGET_QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            Comment parentComment = new Comment();
            parentComment.setCommentCount(1);
            parentComment.setParentId(comment.getParentId());
            commentExtMapper.incComment(parentComment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            //回复不存在异常
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.TARGET_COMMENT_NOT_FOUND);
            }
            Integer row = commentMapper.insert(comment);
            //无法写入数据库异常
            if(!row.equals(1)){
                throw new CustomizeException(CustomizeErrorCode.WRITE_UNKNOWN_ERROR);
            }
            question.setCommentCount(1);
            Integer incComment = questionExtMapper.incComment(question);
            //无法增加回复量异常
            if(!incComment.equals(1)){
                throw new CustomizeException(CustomizeErrorCode.WRITE_UNKNOWN_ERROR);
            }
        }

    }

    @Override
    public List<CommentDTO> ListByQuestionId(Long id, CommentTypeEnum type) {

        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<CommentDTO> commentDTOList = new ArrayList<>();
//        CommentDTO commentDTO = new CommentDTO();
//        for (Comment comment : comments) {
//
//            BeanUtils.copyProperties(comment, commentDTO);
//            User user = userMapper.selectByPrimaryKey(comment.getCommentator());
//            commentDTO.setUser(user);
//            commentDTOList.add(commentDTO);
//        }
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators  = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        //TODO 不懂
        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人转换为MAP
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        //转换comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties( comment,commentDTO);
            commentDTO.setUser(userMap.get((comment.getCommentator())));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
