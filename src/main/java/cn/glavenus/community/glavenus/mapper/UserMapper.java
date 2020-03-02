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
    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    Integer insert(User user);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    @Select("SELECT * FROM user WHERE token = #{token}")
    @Results({
            @Result(property = "accountId",column = "account_id"),
            @Result(property = "gmtCreate",column = "gmt_create"),
            @Result(property = "gmtModified",column = "gmt_modified")
    })
    User findByToken(@Param("token") String token);

    /**
     * 根据accountId查询用户信息
     * @param accountId
     * @return
     */
    @Select("SELECT * FROM user WHERE account_id = #{accountId}")
    @Results({
            @Result(property = "accountId",column = "account_id"),
            @Result(property = "gmtCreate",column = "gmt_create"),
            @Result(property = "gmtModified",column = "gmt_modified")
    })
    User findByUser(@Param("accountId") String accountId);

    /**
     * 修改token
     * @param token
     * @param accountId
     */
    @Update("update user set token=#{token} where account_id=#{accountId}")
    Integer updateToken(@Param("token") String token,
                    @Param("accountId") String accountId);
}
