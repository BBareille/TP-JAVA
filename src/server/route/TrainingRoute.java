package server.route;

import DAL.DAL;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import database.SqlConnection;
import models.Trainee;
import models.Training;
import models.adapter.TraineeAdapter;
import models.adapter.TrainingAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Objects;

public class TrainingRoute extends CRUDRoute{
    public TrainingRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Training(connection);
        this.modelAdapter = new TrainingAdapter(connection);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        String method = exchange.getRequestMethod();
        String uriString = uri.toString();
        if(Objects.equals(uriString, "/"+route)){
            System.out.println(method);
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
        if(uriString.matches("/"+route+"/[0-9]+/trainee")) {
            String[] idSplit = uriString.split("/");
            Integer id = Integer.valueOf(idSplit[2]);
            System.out.println(id);
            try {
                getTraineeInTraining(exchange, id);
            }catch (Exception e){
                System.out.println(e);
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
    }

    private void getTraineeInTraining(HttpExchange exchange, Integer id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        Training training = new Training(connection);
        training = training.findOne(id);
        System.out.println(training.getTrainee());
//        builder.registerTypeAdapter(model.getClass(), modelAdapter);
//        builder.setPrettyPrinting();
//        Gson gson = builder.create();
//        String response = gson.toJson(targetModel);
//        exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
//        exchange.getResponseHeaders().add("Connection", "close");
//        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
//        exchange.sendResponseHeaders(200, response.getBytes().length);
//        OutputStream os = exchange.getResponseBody();
//        os.write(response.getBytes());
//        os.close();
    }

    ;
}
