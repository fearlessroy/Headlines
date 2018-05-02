package com.wyf.async.handler;

import com.wyf.async.EventHandler;
import com.wyf.async.EventModel;
import com.wyf.async.EventType;
import com.wyf.model.Message;
import com.wyf.service.MessageService;
import com.wyf.util.MailSender;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by w7397 on 2017/3/23.
 */
public class RegisterHander implements EventHandler {

	@Autowired
	MessageService messageService;

	@Autowired
	MailSender mailSender;


	@Override
	public void doHandle(EventModel model) {
		Message message = new Message();
		message.setToId(model.getActorId());
		message.setContent("您已经注册成功");
		message.setFromId(3);
		message.setCreatedDate(new Date());
		messageService.addMessage(message);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", model.getExt("username"));
		mailSender.sendWithHTMLTemplate(model.getExt("email"), "注册成功", "mails/welcome.html", map);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.REGISTER);
	}
}
