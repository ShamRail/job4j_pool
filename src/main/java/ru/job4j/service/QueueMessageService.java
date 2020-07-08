package ru.job4j.service;

import ru.job4j.model.QueueMessage;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueMessageService implements MessageService<QueueMessage> {

    private final Queue<QueueMessage> queue = new ConcurrentLinkedQueue<>();

    @Override
    public void put(QueueMessage message) {
        queue.offer(message);
    }

    @Override
    public QueueMessage get(String... location) {
        QueueMessage message = queue.poll();
        return message != null ? message : new QueueMessage();
    }

}
