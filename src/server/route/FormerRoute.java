package server.route;

import DAL.DAL;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.*;
import com.sun.net.httpserver.HttpExchange;
import database.SqlConnection;
import models.Former;
import models.adapter.FormerAdapter;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

public class FormerRoute extends CRUDRoute{
    public FormerRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Former(connection);
        this.modelAdapter = new FormerAdapter();
    }
}
