package ru.job4j.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QueueMessage {

    private String queue;

    private String text;

    public QueueMessage(String queue, String text) {
        this.queue = queue;
        this.text = text;
    }
}
