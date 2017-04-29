package com.wyf.async.handler;

import com.wyf.async.EventHandler;
import com.wyf.async.EventModel;
import com.wyf.async.EventType;
import com.wyf.model.Message;
import com.wyf.service.MessageService;
import com.wyf.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by w7397 on 2017/3/23.
 */
@org.springframework.stereotype.Component
public class LoginExceptionHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel model) {
        //判断是否有异常登录
        Message message = new Message();
        message.setToId(model.getActorId());
        message.setContent("login ip exception");
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", model.getExt("username"));
        mailSender.sendWithHTMLTemplate(model.getExt("email"), "登陆异常", "mails/exception.html",
                map);

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
