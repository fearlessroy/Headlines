package com.wyf.controller;

import com.wyf.aspect.LogAspect;
import com.wyf.model.News;
import com.wyf.model.ViewObject;
import com.wyf.service.NewsService;
import com.wyf.service.UserService;
import com.wyf.util.HeadlineUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by w7397 on 2017/3/18.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);


    @Autowired
    NewsService newsService;

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String index(Model model, @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "rember", defaultValue = "0") int rember) {

        try {
            Map<String, Object> map = userService.register(username, password);
            if (map.isEmpty()) {
                return HeadlineUtil.getJSONString(0, "注册成功");
            } else {
                return HeadlineUtil.getJSONString(1, map);
            }
        } catch (Exception e) {
            logger.error("注册异常", e.getMessage());
            return HeadlineUtil.getJSONString(1, "注册异常");
        }

    }
}
