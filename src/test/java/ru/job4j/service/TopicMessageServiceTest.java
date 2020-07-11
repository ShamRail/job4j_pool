package ru.job4j.service;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.model.TopicMessage;

public class TopicMessageServiceTest {

    @Test
    public void whenAddAndGet() {
        TopicMessageService tms = TopicMessageService.getInstance();
        TopicMessage message = new TopicMessage("a", "b");
        tms.put(message);
        Assert.assertEquals(message, tms.get("a"));
    }

    @Test
    public void whenGetDefault() {
        TopicMessageService tms = TopicMessageService.getInstance();
        TopicMessage message = new TopicMessage();
        Assert.assertEquals(message, tms.get(""));
    }

}