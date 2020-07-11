package ru.job4j.pool;

public interface MessagePool<T, O> {
    O submit(T task);

    enum Operation {
        PUT, GET;
    }

}
