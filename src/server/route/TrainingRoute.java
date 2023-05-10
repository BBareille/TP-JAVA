package server.route;

import DAL.DAL;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import database.SqlConnection;
import models.Former;
import models.Trainee;
import models.Training;
import models.adapter.FormerAdapter;
import models.adapter.TraineeAdapter;
import models.adapter.TrainingAdapter;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            try {
                getTraineeInTraining(exchange, id);
            }catch (Exception e){
                System.out.println(e);
            }
        }
        if(uriString.matches("/"+route+"/[0-9]+/former")) {
            String[] idSplit = uriString.split("/");
            Integer id = Integer.valueOf(idSplit[2]);
            try {
                getFormerInTraining(exchange, id);
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
        List<Trainee> trainees = new ArrayList<>();
        trainees = training.getTrainee();
        builder.registerTypeAdapter(Trainee.class, new TraineeAdapter(connection));
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String response = gson.toJson(trainees);
        exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Connection", "close");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    };

    private void getFormerInTraining(HttpExchange exchange, Integer id) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {
        Training training = new Training(connection);
        training = training.findOne(id);
        List<Former> formers = new ArrayList<>();
        formers = training.getFormer();
        builder.registerTypeAdapter(Former.class, new FormerAdapter(connection));
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String response = gson.toJson(formers);
        exchange.getResponseHeaders().set("Content-type", "application/json; charset=UTF-8");
        exchange.getResponseHeaders().add("Connection", "close");
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
