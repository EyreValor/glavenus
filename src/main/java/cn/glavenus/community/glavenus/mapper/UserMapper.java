package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * Creaked by EyreValor on 2020/2/27
 */
@Mapper
@Component
public interface UserMapper {
    /**
     * 将用户信息写入数据库
     * @param user
     */
    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified,avatar_url) " +
            "values " +
            "(#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatatUrl})")
    Integer insert(User user);

    /**
     * 修改token
     * @param token
     * @param accountId
     */
    @Update("update user set token=#{token} where account_id=#{accountId}")
    Integer updateToken(@Param("token") String token,
                        @Param("accountId") String accountId);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @Select("SELECT * FROM user WHERE token = #{token}")
    User findByToken(@Param("token") String token);

    /**
     * 根据accountId查询用户信息
     * @param accountId
     * @return
     */
    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    User findByUser(@Param("accountId") String accountId);


    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findUserById(@Param("id") Integer id);
}
