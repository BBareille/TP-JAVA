package server;

import com.sun.net.httpserver.HttpServer;
import database.SqlConnection;
import server.route.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    SqlConnection connection;
    public Server(SqlConnection connection){
        this.connection = connection;
    }

    public void startServer() throws IOException {
        int port = 9000;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("server started at localhost:" + port);
        server.createContext("/former", new FormerRoute(connection, "former"));
        server.createContext("/trainee", new TraineeRoute(connection, "trainee"));
        server.createContext("/category", new CategoryRoute(connection, "category"));
        server.createContext("/level", new LevelRoute(connection, "level"));
        server.createContext("/training", new TrainingRoute(connection, "training"));
        server.setExecutor(null);
        server.start();
    }
}
