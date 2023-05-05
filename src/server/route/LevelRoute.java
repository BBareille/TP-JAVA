package server.route;

import database.SqlConnection;
import models.Level;
import models.Training;
import models.adapter.LevelAdapter;
import models.adapter.TrainingAdapter;

public class LevelRoute extends CRUDRoute{
    public LevelRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Level(connection);
        this.modelAdapter = new LevelAdapter(connection);
    }
}
