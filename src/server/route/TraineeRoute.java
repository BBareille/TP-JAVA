package server.route;

import DAL.DAL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.SqlConnection;
import models.Trainee;
import models.adapter.TraineeAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TraineeRoute extends CRUDRoute {

    public TraineeRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Trainee(connection);
        this.modelAdapter = new TraineeAdapter();
    }

//    void getOne(HttpExchange exchange, Integer id) throws SQLException {
//        SqlConnection connection = new SqlConnection();
//        connection.connect();
//        Trainee trainee = new Trainee(connection);
//        try {
//            Trainee traineeList = trainee.findOne(id);
//            GsonBuilder builder = new GsonBuilder();
//            builder.registerTypeAdapter(Trainee.class, new TraineeAdapter());
//            builder.setPrettyPrinting();
//            Gson gson = builder.create();
//            String response = gson.toJson(traineeList);
//            exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
//            exchange.getResponseHeaders().add("Connection", "close");
//            exchange.sendResponseHeaders(200, response.getBytes().length);
//            OutputStream os = exchange.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    void post(HttpExchange exchange) throws IOException, SQLException, IllegalAccessException {
//        try {
//            InputStream body = exchange.getRequestBody();
//            String json = new String(body.readAllBytes(), StandardCharsets.UTF_8);
//            Gson gson = new GsonBuilder()
//                    .registerTypeAdapter(Trainee.class, new TraineeAdapter())
//                    .create();
//
//            Trainee trainee = gson.fromJson(json, Trainee.class);
//            trainee.create(trainee);
//            String traineeJson = gson.toJson(trainee);
//
//            exchange.sendResponseHeaders(200, traineeJson.getBytes().length);
//            OutputStream os = exchange.getResponseBody();
//            os.write(traineeJson.getBytes());
//            os.close();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//    }



}
