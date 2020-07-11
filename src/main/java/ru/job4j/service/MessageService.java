package ru.job4j.service;

public interface MessageService<T> {
    void put(T message);
    T get(String name);
}
