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
        this.modelAdapter = new TraineeAdapter(connection);
    }

}
