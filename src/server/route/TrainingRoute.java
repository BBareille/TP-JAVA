package server.route;

import database.SqlConnection;
import models.Trainee;
import models.Training;
import models.adapter.TraineeAdapter;
import models.adapter.TrainingAdapter;

public class TrainingRoute extends CRUDRoute{
    public TrainingRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Training(connection);
        this.modelAdapter = new TrainingAdapter(connection);
    }
}
