package ru.job4j;

import ru.job4j.contoller.SocketController;

public class Application {
    public static void main(String[] args) {
        new SocketController(9000).handle();
    }
}
