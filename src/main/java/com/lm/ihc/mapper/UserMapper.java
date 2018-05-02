package com.lm.ihc.mapper;

import com.lm.ihc.domain.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select id," +
            "username," +
            "nick," +
            "email," +
            "remark from user where username = #{username}")
    User selectByUsername(@Param("username") String username);

    @Select("select id," +
            "username," +
            "password," + 
            "nick," +
            "email," +
            "remark from user where username = #{username}")
    User selectPwdByUsername(@Param("username") String username);

    @Select("select id," +
            "username," +
            "nick," +
            "email," +
            "remark from user where id = #{id}")
    User selectById(@Param("id") int id);

}
