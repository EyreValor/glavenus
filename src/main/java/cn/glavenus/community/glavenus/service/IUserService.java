package cn.glavenus.community.glavenus.service;

import cn.glavenus.community.glavenus.dto.GithubUser;
import cn.glavenus.community.glavenus.model.User;

import javax.servlet.http.HttpServletRequest;

public interface IUserService {
    /**
     * 获取本地数据库用户信息
     * @param accountId
     * @return
     */
    User getUserByAccountId(String accountId);

    /**
     * 根据guthub的账户信息创建USer
     * @param githubUser
     */
    void createUser(GithubUser githubUser,User user,String token);

    /**
     * 根据accountId修改token
     * @param token
     * @param accoubtId
     * @return
     */
    void updateToken(User user,String token,String accoubtId);
}
