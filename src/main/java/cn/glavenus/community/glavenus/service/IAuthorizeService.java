package cn.glavenus.community.glavenus.service;

import cn.glavenus.community.glavenus.dto.GithubUser;

import java.io.IOException;

public interface IAuthorizeService {

    /**
     * 获取access_token
     * @param code
     * @param state
     * @return
     */
    String getAccessToken(String code,String state) throws IOException;

    /**
     * 获取GithubUser
     * @param accessToken
     * @return
     */
    GithubUser getGithubUser(String accessToken);
}
