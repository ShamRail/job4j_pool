package ru.job4j.contoller;

import ru.job4j.model.HttpRequest;
import ru.job4j.model.HttpResponse;
import ru.job4j.model.QueueMessage;
import ru.job4j.model.TopicMessage;
import ru.job4j.pool.MessagePool;
import ru.job4j.pool.TopicMessagePool;
import ru.job4j.pool.task.QueueMessageTask;
import ru.job4j.pool.task.TopicMessageTask;
import ru.job4j.utils.JSONUtils;

import java.util.Optional;

public class TopicController implements HttpController {


    private final TopicMessagePool pool = new TopicMessagePool();

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        String method = request.getRequestMethod();
        if (!method.equals("GET") && !method.equals("POST")) {
            throw new UnsupportedOperationException();
        }
        if ("GET".equals(method)) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }

    private void doPost(HttpRequest request, HttpResponse response) {
        doSomething(request, response, MessagePool.Operation.PUT);
    }

    private void doGet(HttpRequest request, HttpResponse response) {
        doSomething(request, response, MessagePool.Operation.GET);
    }

    private void doSomething(HttpRequest request, HttpResponse response, MessagePool.Operation operation) {
        Optional<TopicMessage> message = JSONUtils.deserialize(request.getContent(), TopicMessage.class);
        TopicMessageTask task;
        if (operation == MessagePool.Operation.PUT) {
            task = new TopicMessageTask(message.orElse(new TopicMessage()), operation);
        } else {
            String topic = request.getUri().split("/")[1];
            task = new TopicMessageTask(message.orElse(new TopicMessage()), operation, topic);
        }
        TopicMessage out = pool.submit(task);
        response.setContent(JSONUtils.serialize(out));
        response.sendResponse();
    }

}
