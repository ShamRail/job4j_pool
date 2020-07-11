package ru.job4j.service;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.model.QueueMessage;

import static org.junit.Assert.*;

public class QueueMessageServiceTest {

    @Test
    public void putAndGet() {
        QueueMessageService qms = QueueMessageService.getInstance();
        QueueMessage message = new QueueMessage("a", "b");
        qms.put(message);
        Assert.assertEquals(message, qms.get(""));
    }

    @Test
    public void whenGetEmpty() {
        QueueMessageService qms = QueueMessageService.getInstance();
        QueueMessage message = new QueueMessage();
        Assert.assertEquals(message, qms.get(""));
    }

}