package ru.job4j.pool;

import ru.job4j.model.QueueMessage;
import ru.job4j.pool.task.QueueMessageTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class QueueMessagePool implements MessagePool<QueueMessageTask, QueueMessage> {

    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

    @Override
    public QueueMessage submit(QueueMessageTask task) {
        QueueMessage queueMessage = new QueueMessage();
        Future<QueueMessage> future = pool.submit(task);
        try {
            while (!future.isDone()) {
                Thread.sleep(300);
            }
            queueMessage = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return queueMessage;
    }
}
