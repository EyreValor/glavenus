package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    Integer incView(Question record);

    Integer incComment(Question record);

    List<Question> selectRelated(Question record);
}