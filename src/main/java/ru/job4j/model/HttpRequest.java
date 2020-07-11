package ru.job4j.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

@Getter
public class HttpRequest {

    private Map<String, String> headers = new HashMap<>();

    private String content;

    private String requestMethod;

    private String uri = "";

    private HttpRequest() {}

    private void setContent(String content) {
        this.content = content;
    }

    private void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private void setRequestMethod(String methodName) {
        this.requestMethod = methodName;
    }

    private void setURI(String uri) {
        this.uri = uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    public static HttpRequest build(char[] data) {
        HttpRequest httpRequest = new HttpRequest();
        String[] split = headersData(data);
        retrieveMethodAndURI(httpRequest, split[0]);
        retrieveHeadersAndContent(httpRequest, split);
        return httpRequest;
    }

    private static void retrieveHeadersAndContent(HttpRequest httpRequest, String[] split) {
        Map<String, String> headers = new HashMap<>();
        String cnt = "";
        for (int i = 1; i < split.length; i++) {
            if (split[i].isEmpty()) {
                StringJoiner sj = new StringJoiner(System.lineSeparator());
                for (int j = i + 1; j < split.length; j++) {
                    sj.add(split[j]);
                }
                cnt = sj.toString();
                break;
            } else {
                String[] parts = split[i].split(": ");
                headers.put(parts[0], parts[1]);
            }
        }

        httpRequest.setContent(cnt);
        httpRequest.setHeaders(headers);
    }

    private static void retrieveMethodAndURI(HttpRequest httpRequest, String s) {
        String[] partsFirstLine = s.split(" ");
        httpRequest.setRequestMethod(partsFirstLine[0]);
        httpRequest.setURI(partsFirstLine[1]);
    }

    private static String[] headersData(char[] data) {
        String[] split = new String(data).split("\r");
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals("")) {
                break;
            }
            split[i] = split[i].replace("\n", "");
        }

        for (String s : split) {
            if (!s.isEmpty()) {
                System.out.println(s);
            }
        }
        return split;
    }


}
