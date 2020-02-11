package com.xyb.aidemoli.mapper;

import com.xyb.aidemoli.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,comment_count,view_count,like_count,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset")Integer offset, @Param("size")Integer size);

    @Select("select count(*) from question")
    Integer count();

    @Select("select * from question where creator = #{accountId} limit #{offset},#{size}")
    List<Question> listByAccountId(@Param("accountId") String accountId,@Param("offset")Integer offset, @Param("size")Integer size);

    @Select("select count(*) from question where creator = #{accountId}")
    Integer countByAccountId(@Param("accountId") String accountId);

    @Select("select * from question where id = #{id}")
    Question getById(@Param("id") Integer id);
}
