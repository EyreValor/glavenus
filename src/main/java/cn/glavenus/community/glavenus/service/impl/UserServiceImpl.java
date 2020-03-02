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


    @Override
    public User getUserByAccountId(String accountId) {
        User user = findByUser(accountId);
        return user;
    }

    @Override
    public User getUserByToken(String token) {

        return findUserByToken(token);
    }

    @Override
    public void createUser(GithubUser githubUser,User user,String token) {
        //如果数据库中没有创建账户
        user = new User();
        user.setName(githubUser.getName());
        user.setAccountId(String.valueOf(githubUser.getId()));
        //将cookie写入数据库持久保存
        user.setToken(token);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        //将用户数据写入数据库
        insertUser(user);
    }

    @Override
    public void updateToken(String token, String accoubtId) {
        setToken(token,accoubtId);
    }

    @Override
    public void setLoginStatus(HttpServletRequest request) {
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if ("token" .equals(cookie.getName())) {
                    String token = cookie.getValue();
                    user = findUserByToken(token);
                    if (user != null) {
                        //用户不为空时写cookie 和 session
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
    }

    //将user写入数据库
    private void insertUser(User user){
       Integer row =  userMapper.insert(user);
       if(!row.equals(1)){
           //TODO
       }
    }

    //根据accoubtId修改token
    private void setToken(String token,String accountId){
        Integer row = userMapper.updateToken(token, accountId);
        if (!row.equals(1)){
            //TODO
        }
    }
    //根据token获取user
    private User findUserByToken(String token){
        return userMapper.findByToken(token);
    }

    //获取用户信息
    private User findByUser(String accountId){
        if (accountId == null){
            //TODO 规划异常
        }
        return userMapper.findByUser(accountId);
    }

}
