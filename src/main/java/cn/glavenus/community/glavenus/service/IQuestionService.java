package cn.glavenus.community.glavenus.service;

import cn.glavenus.community.glavenus.dto.PageinationDTO;
import cn.glavenus.community.glavenus.dto.QuestionDTO;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.User;

public interface IQuestionService {

    /**
     * 创建提问
     */
    void createQuestion(Question question,User user);

    /**
     * 查询数据库中的问题
     * @return
     */
    PageinationDTO getQuestions(Integer page);

    /**
     * 根据userid查询数据库中的问题
     * @return
     */
    PageinationDTO getQuestions(Integer userid,Integer page);

    /**
     * 根据问题id获取问题详情
     * @param id
     * @return
     */
    QuestionDTO getQuestionById(Integer id);
}
