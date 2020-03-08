package cn.glavenus.community.glavenus.provider;

import cn.glavenus.community.glavenus.dto.AccessTokenDTO;
import cn.glavenus.community.glavenus.dto.GithubUser;
import com.alibaba.fastjson.JSON;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Creaked by EyreValor on 2020/2/25
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        //调用okhttp发送post请求的方法
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));//使用fastJSON包将对象转换成json格式
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //获取返回的access_token
            String string = response.body().string();
            String tokenstr = string.split("&")[0];
            String token = string.split("=")[1];
            return token;
        } catch (IOException e) {
        }
        return null;
    }

    //将获取的access_token传入获取user信息
    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            //接收获取到的user json信息
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
