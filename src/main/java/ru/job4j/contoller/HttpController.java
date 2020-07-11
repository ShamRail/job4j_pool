package ru.job4j.contoller;

import ru.job4j.model.HttpRequest;
import ru.job4j.model.HttpResponse;

public interface HttpController {
    void service(HttpRequest request, HttpResponse response);
}
