package ru.job4j.pool.task;

import ru.job4j.model.QueueMessage;
import ru.job4j.model.TopicMessage;
import ru.job4j.pool.MessagePool;
import ru.job4j.service.QueueMessageService;

public class QueueMessageTask implements MessageTask<QueueMessage> {

    private QueueMessageService service = QueueMessageService.getInstance();

    private QueueMessage message;

    private String queue;

    private MessagePool.Operation operation;

    public QueueMessageTask(QueueMessage message, MessagePool.Operation operation) {
        this.message = message;
        this.operation = operation;
    }

    public QueueMessageTask(QueueMessage message, String queue, MessagePool.Operation operation) {
        this.message = message;
        this.queue = queue;
        this.operation = operation;
    }

    @Override
    public QueueMessage call() {
        QueueMessage queueMessage;
        if (operation == MessagePool.Operation.PUT) {
            service.put(message);
            queueMessage = message;
        } else {
            queueMessage = service.get(queue);
        }
        return queueMessage;
    }
}
