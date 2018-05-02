package com.wyf.controller;

import com.wyf.aspect.LogAspect;
import com.wyf.async.EventProducer;
import com.wyf.service.NewsService;
import com.wyf.service.UserService;
import com.wyf.util.HeadlineUtil;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

	@Autowired
	EventProducer eventProducer;

	@RequestMapping(path = {"/reg/"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String reg(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rember", defaultValue = "0") int rember,
		HttpServletResponse response) {

		try {
			Map<String, Object> map = userService.register(username, password);
			if (map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if (rember > 0) {
					cookie.setMaxAge(3600 * 24 * 5);
				}
				response.addCookie(cookie);

				//eventProducer.fireEvent(new EventModel(EventType.REGISTER).setActorId((int) map.get("userId")).setExt("username", "wyf").setExt("email", "w739709403@126.com"));

				return HeadlineUtil.getJSONString(0, "注册成功");
			} else {
				return HeadlineUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			logger.error("注册异常", e.getMessage());
			return HeadlineUtil.getJSONString(1, "注册异常");
		}

	}

	@RequestMapping(path = {"/login/"}, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam(value = "rember", defaultValue = "0") int rember,
		HttpServletResponse response) {

		try {
			Map<String, Object> map = userService.login(username, password);
			if (map.containsKey("ticket")) {
				Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if (rember > 0) {
					cookie.setMaxAge(3600 * 24 * 5);
				}
				response.addCookie(cookie);
                /*
                eventProducer.fireEvent(new
                        EventModel(EventType.LOGIN).setActorId((int) map.get("userId"))
                        .setExt("username", "wyf").setExt("email", "w739709403@126.com"));
                   */
				return HeadlineUtil.getJSONString(0, "成功");
			} else {
				return HeadlineUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			logger.error("登录异常", e.getMessage());
			return HeadlineUtil.getJSONString(1, "登录异常");
		}

	}

	@RequestMapping(path = {"/logout/"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String logout(@CookieValue("ticket") String ticket) {
		userService.logout(ticket);
		return "redirect:/";
	}
}
