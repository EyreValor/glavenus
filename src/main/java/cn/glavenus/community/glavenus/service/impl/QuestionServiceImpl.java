package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.dto.PageinationDTO;
import cn.glavenus.community.glavenus.dto.QuestionDTO;
import cn.glavenus.community.glavenus.mapper.QuestionMapper;
import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.Question;
import cn.glavenus.community.glavenus.model.QuestionExample;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IQuestionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaked by EyreValor on 2020/3/2
 */
@Service
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Value("${index.page.size}")
    private Integer size;

    /**
     * 创建提问
     * @param user
     */
    @Override
    public void createQuestion(Question parameter,User user) {
        if (parameter.getId() == null){
            parameter.setCreator(user.getId());
            parameter.setGmtCreate(System.currentTimeMillis());
            parameter.setGmtModified(parameter.getGmtCreate());
            foundQuestion(parameter);
        }else {
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(parameter.getId());
            List<Question> question = questionMapper.selectByExample(questionExample);
            if (!question.get(0).getCreator().equals(user.getId())){
                //TODO 创建非法参数异常错误
            }
            parameter.setGmtModified(System.currentTimeMillis());
            int row = questionMapper.updateByExample(parameter, questionExample);
            if (row != 1){
                //TODO 创建写入异常错误
            }
        }
    }

    /**
     * 查询数据库中的问题
     * @param page
     * @return
     */
    @Override
    public PageinationDTO getQuestions(Integer page) {
        //获取分页数
        Integer totalPage = getTotalPage();
        //设置page的数值范围
        page = limitPage(page,totalPage);
        //获取查询页码的问题列表
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
        //获取pageinationDto
        PageinationDTO pageinationDTO = getPageinationDTO(questionList,page,totalPage);
        //返回问题列表
        return pageinationDTO;
    }

    /**
     * 根据userid查询数据库中的问题
     * @param userId
     * @param page
     * @return
     */
    @Override
    public PageinationDTO getQuestions(Integer userId,Integer page) {
        Integer totalPage = getTotalPageByUserId(userId);
        //设置page的数值范围
        page = limitPage(page,totalPage);
        //获取查询页码的问题列表
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        questionExample.setOrderByClause("gmt_create desc");
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        //获取pageinationDto
        PageinationDTO pageinationDTO = getPageinationDTO(questionList,page,totalPage);
        //返回问题列表
        return pageinationDTO;
    }


    /**
     * 创建提问
     * @param question
     */
    private void foundQuestion(Question question) {
        Integer row = questionMapper.insert(question);
        if (!row.equals(1)) {
            //TODO
        }
    }
    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    private User findUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    /**
     * 根据问题id获取问题详情
     * @param id
     * @return
     */
    @Override
    public QuestionDTO getQuestionById(Integer id) {
        //TODO 处理异常
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andIdEqualTo(id);
        List<Question> questionList = questionMapper.selectByExampleWithBLOBs(questionExample);
        if (questionList.size() == 0){
            //TODO
        }
        Question question = questionList.get(0);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUserAvatarUrl(user.getAvatarUrl());
        questionDTO.setUserName(user.getName());
        return questionDTO;
    }

    /**
     * 获取分页数
     * @return
     */
    public Integer getTotalPage(){
        List<Question> questionList = questionMapper.selectByExample(new QuestionExample());
        Integer totalPage = questionList.size();
        if (totalPage % size == 0) {
            totalPage = totalPage / size;
        } else {
            totalPage = totalPage / size + 1;
        }
        return totalPage;
    }

    /**
     * 根据用户id获取分页数
     * @return
     */
    public Integer getTotalPageByUserId(Integer userId){
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> questionList = questionMapper.selectByExample(questionExample);
        Integer totalPage = questionList.size();
        if (totalPage % size == 0) {
            totalPage = totalPage / size;
        } else {
            totalPage = totalPage / size + 1;
        }
        return totalPage;
    }

    /**
     * 限制page的值防止非法参数
     * @param page
     * @param totalPage
     * @return
     */
    private Integer limitPage(Integer page, Integer totalPage) {
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        return  page;
    }

    /**
     * 获取pageinationDto
     * @param questions
     * @param page
     * @param totalPage
     * @return
     */
    private PageinationDTO getPageinationDTO(List<Question> questions, Integer page, Integer totalPage) {
        //创建DTO
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        PageinationDTO pageinationDTO = new PageinationDTO();
        for (Question question : questions) {
            //获取用户信息
            User user = findUserById(question.getCreator());
            if (user == null) {
                continue;
            }
            QuestionDTO questionDTO = new QuestionDTO();
            //将question的值赋值到DTO中
            BeanUtils.copyProperties(question, questionDTO);
            //将user赋值到DTO中
            questionDTO.setUserAvatarUrl(user.getAvatarUrl());
            questionDTO.setUserName(user.getName());
            questionDTOList.add(questionDTO);
        }
        pageinationDTO.setQuestions(questionDTOList);
        pageinationDTO.setPagsination(totalPage, page, size);
        return pageinationDTO;
    }
}
