package com.xyb.aidemoli.mapper;

import com.xyb.aidemoli.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,bio,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id = #{accountId}")
    User findByAccountId(String accountId);

    @Update("update user set name = #{name},avatar_url = #{avatarUrl},token = #{token},bio=#{bio},gmt_modified=#{gmtModified} where id = #{id}")
    void update(User dbUser);
}
