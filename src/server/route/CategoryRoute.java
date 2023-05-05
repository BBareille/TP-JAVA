package server.route;

import database.SqlConnection;
import models.Category;
import models.Training;
import models.adapter.CategoryAdapter;
import models.adapter.TrainingAdapter;

public class CategoryRoute extends CRUDRoute {
    public CategoryRoute(SqlConnection connection, String route) {
        super(connection, route);
        this.model = new Category(connection);
        this.modelAdapter = new CategoryAdapter(connection);
    }
}
