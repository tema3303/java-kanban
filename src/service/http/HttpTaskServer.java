package service.http;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import service.manager.Managers;
import service.manager.TaskManager;
import tasks.Epic;
import tasks.SubTask;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static jdk.internal.util.xml.XMLStreamWriter.DEFAULT_CHARSET;

public class HttpTaskServer {

    public static final int PORT = 8080;
    private final TaskManager taskManager;
    private final HttpServer server;
    private final Gson gson;

    public HttpTaskServer() throws IOException {
        gson = Managers.getGson();
        this.taskManager = Managers.getDefault();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", this::handler);
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer httpTaskServer = new HttpTaskServer();
        httpTaskServer.server.start();
    }


    private void handler(HttpExchange exchange) {
        try {
            final String path = exchange.getRequestURI().getPath().replaceFirst("/tasks/", "");
            switch (path) {
                case "task":
                    handleTasks(exchange);
                    break;
                case "subtask":
                    handleSubtask(exchange);
                    break;
                case "epic":
                    handleEpic(exchange);
                    break;
                case "history":
                    handleHistory(exchange);
                    break;
                default:
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            exchange.close();
        }
    }

    private void handleHistory(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            List<Task> history = taskManager.getTaskHistory();
            String response = gson.toJson(history);
            sendText(exchange, response);
        }
    }

    private void handleEpic(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idEpic = query.substring(3);
                    Epic epic = (Epic) taskManager.getIdEpic(Integer.parseInt(idEpic));
                    String response = gson.toJson(epic);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getAllEpic());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Epic epic = gson.fromJson(body, Epic.class);
                    taskManager.addEpic(epic);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idEpic = query.substring(3);
                    taskManager.removeByIdEpic(Integer.parseInt(idEpic));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.removeAllEpic();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }

    private void handleSubtask(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idSub = query.substring(3);
                    SubTask subTask = (SubTask) taskManager.getIdSubTask(Integer.parseInt(idSub));
                    String response = gson.toJson(subTask);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getAllSubTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    SubTask subTask = gson.fromJson(body, SubTask.class);
                    taskManager.addSubTask(subTask);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idSub = query.substring(3);
                    taskManager.removeByIdSubTask(Integer.parseInt(idSub));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.removeAllSubTask();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }

    private void handleTasks(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String query = exchange.getRequestURI().getQuery();
        InputStream inputStream = exchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
        switch (requestMethod) {
            case "GET": {
                if (query != null) {
                    String idTask = query.substring(3);
                    Task task = taskManager.getIdTask(Integer.parseInt(idTask));
                    String response = gson.toJson(task);
                    sendText(exchange, response);
                    return;
                } else {
                    String response = gson.toJson(taskManager.getAllTask());
                    sendText(exchange, response);
                    return;
                }
            }
            case "POST": {
                try {
                    Task task = gson.fromJson(body, Task.class);
                    taskManager.addTask(task);
                    exchange.sendResponseHeaders(200, 0);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return;
            }
            case "DELETE": {
                if (query != null) {
                    String idTask = query.substring(3);
                    taskManager.removeByIdTask(Integer.parseInt(idTask));
                    exchange.sendResponseHeaders(200, 0);
                } else {
                    taskManager.removeAllTask();
                    exchange.sendResponseHeaders(200, 0);
                }
            }
        }
    }

    public void start() {
        System.out.println("Запускаем сервер Задач " + PORT);
        System.out.println("http://localhost:" + PORT + "/tasks");
        server.start();
    }

    public void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        byte[] resp = text.getBytes(UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, resp.length);
        exchange.getResponseBody().write(resp);
    }
}