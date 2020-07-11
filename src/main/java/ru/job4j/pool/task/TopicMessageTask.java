package ru.job4j.pool.task;

import ru.job4j.model.TopicMessage;
import ru.job4j.pool.MessagePool;
import ru.job4j.service.TopicMessageService;

public class TopicMessageTask implements MessageTask<TopicMessage> {

    private TopicMessageService service = TopicMessageService.getInstance();

    private TopicMessage message;

    private MessagePool.Operation operation;

    private String topic;

    public TopicMessageTask(TopicMessage message, MessagePool.Operation operation) {
        this.message = message;
        this.operation = operation;
    }

    public TopicMessageTask(TopicMessage message, MessagePool.Operation operation, String topic) {
        this.message = message;
        this.operation = operation;
        this.topic = topic;
    }

    @Override
    public TopicMessage call() {
        TopicMessage topicMessage;
        if (operation == MessagePool.Operation.PUT) {
            service.put(message);
            topicMessage = message;
        } else {
            topicMessage = service.get(topic);
        }
        return topicMessage;
    }
}
