package com.lm.ihc.mapper;

import com.lm.ihc.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

public interface UserMapper {

    @Select("select id," +
            "username," +
            "nick," +
            "password," +
            "email," +
            "remark," +
            "state from user where state = 1")
    List<User> selectAll();

    @Select("select id," +
            "username," +
            "nick," +
            "email," +
            "remark," +
            "state from user where username = #{username}")
    User selectByUsername(@Param("username") String username);

    @Select("select id," +
            "username," +
            "nick," +
            "email," +
            "remark," +
            "state from user where nick = #{nick}")
    User selectByNick(@Param("nick") String nick);

    @Select("select id," +
            "username," +
            "password," + 
            "nick," +
            "email," +
            "state," +
            "remark from user where username = #{username} and state = 1")
    User selectPwdByUsername(@Param("username") String username);

    @Select("select id," +
            "username," +
            "nick," +
            "email," +
            "state," +
            "remark from user where id = #{id}")
    User selectById(@Param("id") int id);

    @Insert("INSERT INTO user(" +
            "username," +
            "password," +
            "nick," +
            "email," +
            "remark," +
            "state)" +
            "VALUES (" +
            "#{username}," +
            "#{password}," +
            "#{nick}," +
            "#{email}," +
            "#{remark}," +
            "#{state})")
    Integer insertOne(User user);

    @UpdateProvider(type = UserDaoProvider.class, method = "upd")
    Integer updOne(User user);

    class UserDaoProvider{
        public String upd(User user) {
            String sql = "UPDATE user SET ";
            String s = "";
            if ((s = user.getUsername()) != null) {
                sql += "username = '" + s + "', ";
            }
            if ((s = user.getPassword()) != null) {
                sql += "password = '" + s + "', ";
            }
            if ((s = user.getNick()) != null) {
                sql += "nick = '" + s + "', ";
            }
            if ((s = user.getEmail()) != null) {
                sql += "email = '" + s + "', ";
            }
            if ((s = user.getRemark()) != null) {
                sql += "remark = '" + s + "', ";
            }
            sql += "state = " + user.getState();
            sql += " where id = " + user.getId();
            return sql;
        }
    }

}
