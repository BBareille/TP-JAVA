package server;

import com.sun.net.httpserver.HttpServer;
import server.route.RouteHandler;
import server.route.TraineeRoute;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void startServer() throws IOException {
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at localhost:" + port);
        server.createContext("/", new RouteHandler());
        server.createContext("/trainee", new TraineeRoute());
        server.setExecutor(null);
        server.start();
    }
}
