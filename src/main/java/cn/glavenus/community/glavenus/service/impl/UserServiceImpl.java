package cn.glavenus.community.glavenus.service.impl;

import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.mapper.UserMapper;
import cn.glavenus.community.glavenus.model.User;
import cn.glavenus.community.glavenus.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Creaked by EyreValor on 2020/3/2
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取本地数据库用户信息
     * @param accountId
     * @return
     */
    @Override
    public User getUserByAccountId(String accountId) {
        User user = findByUser(accountId);
        return user;
    }

    /**
     * 根据guthub的账户信息创建USer
     * @param githubUser
     * @param user
     * @param token
     */
    @Override
    public void createUser(GithubUser githubUser, User user, String token) {
        //如果数据库中没有创建账户
        user = new User();
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        //将cookie写入数据库持久保存
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setAvatarUrl(githubUser.getAvatar_url());
        //将用户数据写入数据库
        insertUser(user);
    }

    /**
     * 根据accountId修改token
     * @param token
     * @param accoubtId
     */
    @Override
    public void updateToken(String token, String accoubtId) {
        setToken(token, accoubtId);
    }


    //将user写入数据库
    private void insertUser(User user) {
        Integer row = userMapper.insert(user);
        if (!row.equals(1)) {
            //TODO
        }
    }

    //根据accoubtId修改token
    private void setToken(String token, String accountId) {
        Integer row = userMapper.updateToken(token, accountId);
        if (!row.equals(1)) {
            //TODO
        }
    }

    //获取用户信息
    private User findByUser(String accountId) {
        if (accountId == null) {
            //TODO 规划异常
        }
        return userMapper.findByUser(accountId);
    }

}
