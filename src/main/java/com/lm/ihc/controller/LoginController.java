package com.lm.ihc.controller;

import com.lm.ihc.domain.LoginUser;
import com.lm.ihc.domain.User;
import com.lm.ihc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginUser login(@RequestBody User user) {
        // 从数据库获取当前用户信息
        User dataUser = this.userService.queryPwdByUsername(user.getUsername());

        LoginUser loginUser = new LoginUser();
        if (!user.getUsername().equals(dataUser.getUsername())) {
            // 用户名不存在
            loginUser.setStatus(2);
        } else {
            // 加密密码
            String encryptPwd = encryptPwd(dataUser.getUsername(), dataUser.getPassword());
            if (user.getPassword().equals(encryptPwd)) {
                loginUser.setStatus(1);
                loginUser.setUser(dataUser);
            } else {
                // 密码错误
                loginUser.setStatus(0);
            }
        }
        return loginUser;
    }

    /**
     * 加密密码
     *
     * @param username
     * @param pwd
     * @return
     */
    private String encryptPwd(String username, String pwd) {
        String md5Pwd1 = getMd5(username + pwd);
//            String md5Str = md5.digest((username + pwd).getBytes()).toString();
//             截取1-6
        String subMd5Pwd1 = md5Pwd1.substring(1, 6);
//             再次MD5
        md5Pwd1 = getMd5( md5Pwd1 + subMd5Pwd1);
        return Base64.getEncoder().encodeToString(md5Pwd1.getBytes());
    }

    public String getMd5(String string) {
        try {
            // md5加密
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(string.getBytes("UTF-8"));
            byte[] md5Str = md5.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < md5Str.length; i++) {
                if (Integer.toHexString(0xFF & md5Str[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & md5Str[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & md5Str[i]));
            }

            return md5StrBuff.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
