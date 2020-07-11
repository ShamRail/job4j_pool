package ru.job4j.contoller;

import ru.job4j.model.HttpRequest;
import ru.job4j.model.HttpResponse;
import ru.job4j.model.QueueMessage;
import ru.job4j.pool.MessagePool;
import ru.job4j.pool.QueueMessagePool;
import ru.job4j.pool.task.QueueMessageTask;
import ru.job4j.utils.JSONUtils;

import java.util.Optional;

public class QueueController implements HttpController {

    private final QueueMessagePool pool = new QueueMessagePool();

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
        Optional<QueueMessage> message = JSONUtils.deserialize(request.getContent(), QueueMessage.class);
        QueueMessageTask task;
        if (operation == MessagePool.Operation.PUT) {
            task = new QueueMessageTask(message.orElse(new QueueMessage()), operation);
        } else {
            String queue = request.getUri().split("/")[1];
            task = new QueueMessageTask(message.orElse(new QueueMessage()), queue, operation);
        }
        QueueMessage out = pool.submit(task);
        response.setContent(JSONUtils.serialize(out));
        response.sendResponse();
    }

}
