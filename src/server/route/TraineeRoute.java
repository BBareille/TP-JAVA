package server.route;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.SqlConnection;
import models.Trainee;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TraineeRoute implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();
        String uriString = uri.toString();
        if(Objects.equals(uriString, "/trainee")){
            if (method.matches("POST")) {
                try {
                    this.post();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    this.getAll(exchange);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(uriString.matches("/trainee/[0-9]+")){
            String[] idSplit = uriString.split("trainee/");
            Integer id = Integer.valueOf(idSplit[1]);

            if(method.matches("GET")) {

                try {
                    this.getOne();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("PUT")) {
                try {
                    this.update();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("DELETE")) {
                try {
                    this.delete();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    void post(){

    }

    void getAll(HttpExchange exchange) throws SQLException {
        SqlConnection connection = new SqlConnection();
        connection.connect();
        Trainee trainee = new Trainee(connection);
        try {
            List<Trainee> traineeList = trainee.findAll();
            //6System.out.println(traineeList);
            String response = new Gson().toJson(traineeList);
           // System.out.println("efiknefjbegbje" + response);
            exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
            exchange.getResponseHeaders().add("Connection", "close");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getOne(){

    }

    void update(){

    }
    void delete(){

    }

}
