package com.lm.ihc.controller;

import com.lm.ihc.domain.User;
import com.lm.ihc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<User> getAll() {
        return this.userService.queryAll();
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public Integer add(@RequestBody User user) {
        return this.userService.addOne(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.PUT)
    public Integer upd(@RequestBody User user) {
        return this.userService.updOne(user);
    }

    @CrossOrigin
    @RequestMapping(value = "/upd", method = RequestMethod.DELETE)
    public Integer del(@RequestBody User user) {
        return this.userService.updOne(user);
    }
}
