package cn.glavenus.community.glavenus.service;

import cn.glavenus.community.glavenus.dto.CommentDTO;
import cn.glavenus.community.glavenus.enums.CommentTypeEnum;
import cn.glavenus.community.glavenus.model.Comment;

import java.util.List;

public interface ICommentService {
    void insert(Comment comment);

    List<CommentDTO> ListByQuestionId(Long id, CommentTypeEnum type);
}
