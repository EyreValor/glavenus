package cn.glavenus.community.glavenus.mapper;

import cn.glavenus.community.glavenus.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Creaked by EyreValor on 2020/2/27
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (account_id, name, token, gmt_create, gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
