package com.lm.ihc.service;

import com.lm.ihc.domain.User;
import com.lm.ihc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryByUsername(String username) {
        return this.userMapper.selectByUsername(username);
    }
    public User queryByNick(String nick) {
        return this.userMapper.selectByNick(nick);
    }
    public User queryPwdByUsername(String username) {
        return this.userMapper.selectPwdByUsername(username);
    }

    public List<User> queryAll() {
        return this.userMapper.selectAll();
    }

    public Integer addOne(User user) {
        return this.userMapper.insertOne(user);
    }

    public Integer updOne(User user) {
        return this.userMapper.updOne(user);
    }
}
