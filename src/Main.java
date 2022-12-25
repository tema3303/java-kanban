import service.http.HttpTaskServer;
import service.http.KVServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        new KVServer().start();
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }
}