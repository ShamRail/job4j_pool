package ru.job4j.pool.task;

import java.util.concurrent.Callable;

public interface MessageTask<T> extends Callable<T> {
}
