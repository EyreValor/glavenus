package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * Creaked by EyreValor on 2020/3/1
 */
@Mapper
@Component
public interface QuestionMapper {

    @Insert("INSERT INTO question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "VALUES " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    Integer foundQuestion (Question question);
}
