package ru.job4j.utils;

import org.junit.Assert;
import org.junit.Test;
import ru.job4j.model.QueueMessage;
import ru.job4j.model.TopicMessage;

import java.util.Optional;

public class JSONUtilsTest {

    @Test
    public void whenSerializeTopicMessage() {
        TopicMessage message = new TopicMessage("topic", "text");
        String result = JSONUtils.serialize(message);
        String expect = "{\"topic\":\"topic\",\"text\":\"text\"}";
        Assert.assertEquals(expect, result);
    }

    @Test
    public void whenDeserializeTopicMessage() {
        String input = "{\"topic\":\"topic\",\"text\":\"text\"}";
        Optional<TopicMessage> result = JSONUtils.deserialize(input, TopicMessage.class);
        TopicMessage expect = new TopicMessage("topic", "text");
        Assert.assertEquals(expect, result.get());
    }

    @Test
    public void whenSerializeQueueMessage() {
        QueueMessage message = new QueueMessage("topic", "text");
        String result = JSONUtils.serialize(message);
        String expect = "{\"queue\":\"topic\",\"text\":\"text\"}";
        Assert.assertEquals(expect, result);
    }

    @Test
    public void whenDeserializeQueueMessage() {
        String input = "{\"queue\":\"topic\",\"text\":\"text\"}";
        Optional<QueueMessage> result = JSONUtils.deserialize(input, QueueMessage.class);
        QueueMessage expect = new QueueMessage("topic", "text");
        Assert.assertEquals(expect, result.get());
    }

}