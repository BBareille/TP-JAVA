package server.route;

import DAL.DAL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.*;
import com.sun.net.httpserver.*;
import database.SqlConnection;
import models.adapter.TraineeAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public abstract class CRUDRoute implements HttpHandler {

    SqlConnection connection;
    DAL model;
    GsonBuilder builder;
    TypeAdapter modelAdapter;

    String route;
     public CRUDRoute(SqlConnection connection, String route){
        this.connection = connection;
        this.builder = new GsonBuilder();
        this.route = route;
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException{
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();
        String uriString = uri.toString();
        if(Objects.equals(uriString, "/"+route)){
            if (method.matches("POST")) {
                try {
                    post(exchange);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    getAll(exchange);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if(uriString.matches("/"+route+"/[0-9]+")){
            String[] idSplit = uriString.split(route+"/");
            Integer id = Integer.valueOf(idSplit[1]);

            if(method.matches("GET")) {
                try {
//                    this.getOne(exchange, id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("PUT")) {
                try {
//                    this.update();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("DELETE")) {
                try {
//                    this.delete();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };


    protected void getAll(HttpExchange exchange) throws SQLException{
        try {
            List<DAL> modelList = model.findAll();
            builder.registerTypeAdapter(model.getClass(), modelAdapter);
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            String response = gson.toJson(modelList);
            exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
            exchange.getResponseHeaders().add("Connection", "close");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    protected void post(HttpExchange exchange){

    }
}
