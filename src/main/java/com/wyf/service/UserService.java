package com.wyf.service;

import com.wyf.dao.UserDAO;
import com.wyf.model.User;
import com.wyf.util.HeadlineUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by w7397 on 2017/3/18.
 */
@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("magname", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("magpwd", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user != null) {
            map.put("magname", "用户名已经被注册");
        }
//密码强度
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(HeadlineUtil.MD5(password + user.getSalt()));
        //user.setPassword(password);
        userDAO.addUser(user);

        return map;
    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
