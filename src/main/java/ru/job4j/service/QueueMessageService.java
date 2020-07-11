package ru.job4j.service;

import ru.job4j.model.QueueMessage;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueMessageService implements MessageService<QueueMessage> {

    private final Map<String, Queue<QueueMessage>> broker = new ConcurrentHashMap<>();

    private QueueMessageService() {}

    public static QueueMessageService getInstance() {
        return Holder.INSTANCE;
    }

    private final static class Holder {
        private final static QueueMessageService INSTANCE = new QueueMessageService();
    }

    @Override
    public void put(QueueMessage message) {
        Queue<QueueMessage> queue = broker.getOrDefault(message.getQueue(), new ConcurrentLinkedQueue<>());
        if (queue.isEmpty()) {
            queue.offer(message);
            broker.put(message.getQueue(), queue);
        }
    }

    @Override
    public QueueMessage get(String topic) {
        QueueMessage message = new QueueMessage();
        Queue<QueueMessage> queue = broker.get(topic);
        if (queue == null || queue.isEmpty()) {
            return message;
        }
        message = queue.poll();
        return message;
    }

}
