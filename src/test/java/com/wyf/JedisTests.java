package com.wyf;

import com.wyf.dao.CommentDAO;
import com.wyf.dao.LoginTicketDAO;
import com.wyf.dao.NewsDAO;
import com.wyf.dao.UserDAO;
import com.wyf.model.*;
import com.wyf.util.JedisAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HeadlineApplication.class)
public class JedisTests {
    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void testJedis() {
        jedisAdapter.set("hello", "world");
        Assert.assertEquals("world", jedisAdapter.get("hello"));
    }
    @Test
    public void testObject() {
        User user = new User();
        user.setHeadUrl("http://images.wyf.com/head/100t.png");
        user.setName("user1");
        user.setPassword("abc");
        user.setSalt("def");
        jedisAdapter.setObject("user1", user);

        User u = jedisAdapter.getObject("user1", User.class);
        System.out.print(ToStringBuilder.reflectionToString(u));

    }

}
