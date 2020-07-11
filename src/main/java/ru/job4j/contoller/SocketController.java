package ru.job4j.contoller;

import ru.job4j.model.HttpRequest;
import ru.job4j.model.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketController implements Controller {

    private final int port;

    private final Map<String, HttpController> requestDispatcher = new ConcurrentHashMap<>(Map.of(
            "/topic", new TopicController(),
            "/queue", new QueueController()
    ));

    public SocketController(int port) {
        this.port = port;
    }

    @Override
    public void handle() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     InputStream in = socket.getInputStream()) {
                    Thread.sleep(100);
                    int count = in.available();
                    if (count != 0) {

                        char[] content = readContent(count, in);
                        HttpRequest request = HttpRequest.build(content);
                        HttpResponse response = new HttpResponse();
                        response.setOut(out);

                        String uri = request.getUri();

                        String key = uri.startsWith("/topic") ? "/topic" : uri.startsWith("/queue") ? "/queue" : "";
                        HttpController controller = requestDispatcher.get(key);
                        if (controller != null) {
                            controller.service(request, response);
                        }

                    }
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static char[] readContent(int count, InputStream in) throws IOException {
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            char c = (char) in.read();
            chars[i] = c;
        }
        return chars;
    }

    public static void main(String[] args) {
        System.out.println((char) (-1));
    }

}
