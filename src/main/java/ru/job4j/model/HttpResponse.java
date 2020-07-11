package ru.job4j.model;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.OutputStream;

@Getter
@Setter
public class HttpResponse {

    private String content;

    private OutputStream out;

    public void sendResponse() {
        try {
            out.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            out.write((content + "\r\n").getBytes());
            out.flush();
        } catch (Exception e) {

        }
    }

}
