package ru.job4j.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TopicMessage {

    private String topic;

    private String text;

    public TopicMessage(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }
}
