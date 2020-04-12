package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.Comment;
import cn.glavenus.community.glavenus.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
   Integer incComment(Comment comment);
}