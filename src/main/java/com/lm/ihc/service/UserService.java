package com.lm.ihc.service;

import com.lm.ihc.domain.User;
import com.lm.ihc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User queryByUsername(String username) {
        return this.userMapper.selectByUsername(username);
    }
    public User queryPwdByUsername(String username) {
        return this.userMapper.selectPwdByUsername(username);
    }


}
