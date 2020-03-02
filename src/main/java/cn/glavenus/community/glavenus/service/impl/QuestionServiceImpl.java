package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.mapper.QuestionMapper;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Creaked by EyreValor on 2020/3/2
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void createQuestion(String title, String description, String tag, User user) {
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        foundQuestion(question);
    }

    /**
     * 将问题写入数据库
      * @param question
     */
    private void foundQuestion(Question question){
        Integer row = questionMapper.foundQuestion(question);
        if (!row.equals(1)){
            //TODO
        }
    }
}
