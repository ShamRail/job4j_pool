package ru.job4j.service;

import ru.job4j.model.TopicMessage;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicMessageService implements MessageService<TopicMessage> {

    private final Map<String, Queue<TopicMessage>> topics = new ConcurrentHashMap<>();

    @Override
    public void put(TopicMessage message) {
        topics.putIfAbsent(message.getTopic(), new ConcurrentLinkedQueue<>());
        topics.computeIfPresent(message.getTopic(), (s, topicMessages) -> {
            topicMessages.offer(message);
            return topicMessages;
        });
    }

    @Override
    public TopicMessage get(String... topic) {
        TopicMessage message = new TopicMessage();
        if (topic.length == 0) {
            return message;
        }
        Queue<TopicMessage> queue = topics.get(topic[0]);
        if (queue == null || queue.isEmpty()) {
            return message;
        }
        message = queue.poll();
        return message;
    }

}
