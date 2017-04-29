package com.wyf.async.handler;

import com.wyf.async.EventHandler;
import com.wyf.async.EventModel;
import com.wyf.async.EventType;
import com.wyf.model.Message;
import com.wyf.model.User;
import com.wyf.service.MessageService;
import com.wyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by w7397 on 2017/3/23.
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        User user = userService.getUser(model.getActorId());
        //message.setToId(model.getEntityOwnerId()); #true
        message.setToId(model.getActorId());
        message.setContent("user " + user.getName() +
                " like your news,http://127.0.0.1:8080/news/"
                + String.valueOf(model.getEntityId()));
        // SYSTEM ACCOUNT
        message.setFromId(3);
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
