package ru.job4j.service;

import ru.job4j.model.TopicMessage;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicMessageService implements MessageService<TopicMessage> {

    private final Map<String, Queue<TopicMessage>> broker = new ConcurrentHashMap<>();

    private TopicMessageService() { }

    private final static class Holder {
        private final static TopicMessageService INSTANCE = new TopicMessageService();
    }

    public static TopicMessageService getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public void put(TopicMessage message) {
        Queue<TopicMessage> queue = broker.getOrDefault(message.getTopic(), new ConcurrentLinkedQueue<>());
        if (queue.isEmpty()) {
            queue.offer(message);
            broker.put(message.getTopic(), queue);
        }
    }

    @Override
    public TopicMessage get(String topic) {
        TopicMessage message = new TopicMessage();
        for (String queueName : broker.keySet()) {
            if (topic.equals(queueName)) {
                Queue<TopicMessage> queueMessages = broker.get(queueName);
                if (!queueMessages.isEmpty()) {
                    TopicMessage head = queueMessages.poll();
                    if (head != null) {
                        message = head;
                        break;
                    }
                }
            }
        }
        return message;
    }

}
