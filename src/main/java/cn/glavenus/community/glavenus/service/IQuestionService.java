package cn.glavenus.community.glavenus.service;

import cn.glavenus.community.glavenus.model.User;

public interface IQuestionService {

    /**
     * 创建提问
     * @param title
     * @param description
     * @param tag
     */
    void createQuestion(String title, String description, String tag, User user);
}
