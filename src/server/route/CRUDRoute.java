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
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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
                    getOne(exchange, id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("PUT")) {
                try {
                    update(exchange, id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else if (method.matches("DELETE")) {
                try {
                    delete(exchange, id);
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
            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.getResponseHeaders().add("Connection", "close");
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
    protected void post(HttpExchange exchange) throws IOException, SQLException, IllegalAccessException {
        try{
            InputStream body = exchange.getRequestBody();
            String json = new String(body.readAllBytes(), StandardCharsets.UTF_8);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(model.getClass(), modelAdapter)
                    .create();

            DAL newModel = gson.fromJson(json, model.getClass());
            newModel.create(newModel);
            String modelJson = gson.toJson(newModel);

            exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            exchange.sendResponseHeaders(200, modelJson.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(modelJson.getBytes());
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected void getOne(HttpExchange exchange, Integer id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        DAL targetModel = model.findOne(id);
        builder.registerTypeAdapter(model.getClass(), modelAdapter);
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String response = gson.toJson(targetModel);
        exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Connection", "close");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
    protected void update(HttpExchange exchange, Integer id) throws IOException, SQLException, IllegalAccessException {
        InputStream body = exchange.getRequestBody();
        String json = new String(body.readAllBytes(), StandardCharsets.UTF_8);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(model.getClass(), modelAdapter)
                .create();

        DAL newModel = gson.fromJson(json, model.getClass());
        newModel.update(newModel);
        String modelJson = gson.toJson(newModel);

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, modelJson.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(modelJson.getBytes());
        os.close();
    }
    protected void delete(HttpExchange exchange, Integer id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        DAL targetModel = model.findOne(id);
        model.delete(id);
        builder.registerTypeAdapter(model.getClass(), modelAdapter);
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String response = gson.toJson(targetModel);
        exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Connection", "close");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
