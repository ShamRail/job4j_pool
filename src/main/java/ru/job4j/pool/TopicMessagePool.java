package ru.job4j.pool;

import ru.job4j.model.TopicMessage;
import ru.job4j.pool.task.TopicMessageTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TopicMessagePool implements MessagePool<TopicMessageTask, TopicMessage> {

    private ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);

    @Override
    public TopicMessage submit(TopicMessageTask task) {
        TopicMessage topicMessage = new TopicMessage();
        Future<TopicMessage> future = pool.submit(task);
        try {
            while (!future.isDone()) {
                Thread.sleep(300);
            }
            topicMessage = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topicMessage;
    }
}
