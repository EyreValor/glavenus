package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creaked by EyreValor on 2020/3/1
 */
@Mapper
@Component
public interface QuestionMapper {

    /**
     * 创建问题
     * @param question
     * @return
     */
    @Insert("INSERT INTO question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "VALUES " +
            "(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    Integer foundQuestion (Question question);

    /**
     * 首页获取问题
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question order by gmt_create desc limit #{offset},#{size}")
    List<Question> findQuestions(@Param("offset") Integer offset,
                                 @Param("size") Integer size);

    /**
     * 根据userId获取问题
     * @param creator
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question where creator = #{creator} order by gmt_create desc limit #{offset},#{size}")
    List<Question> findQuestionsById(@Param("creator") String creator,
                                 @Param("offset") Integer offset,
                                 @Param("size") Integer size);

    /**
     * 获取所有问题数量
     * @return
     */
    @Select("select count(1) from question")
    Integer findQuestionSize();

    /**
     * 根据userid获取问题数量
     * @param creator
     * @return
     */
    @Select("select count(1) from question where creator = #{creator}")
    Integer findQuestionSizeById(@Param("creator") String creator);

    /**
     * 根据id获取问题详情
     * @param id
     * @return
     */
    @Select("select * from question where id = #{id}")
    Question findQuestionByid(@Param("id") Integer id);
}
