import DAL.IDAL;
import database.SqlConnection;
import models.Category;
import models.Former;
import models.Level;
import models.Training;

import java.util.Date;

public class Main {
    public static void main(String[] args){
        SqlConnection connection = new SqlConnection();
        try{
            connection.connect();



//            Category category = new Category(connection);
//            category.setName("Développement");
//            category.create(category);

            Level level = new Level(connection);
            level.setName("Medium");
            level.create(level);


            Training training = new Training(connection);
            training.setName("NodeJS");
            training.setStart_at(new Date());
            training.setCategory(3L);
            training.setDuration(6);
            training.setPrice(1500);
            training.setOnline(true);
            training.setLevel(level.getId());

            Former forme = new Former();
            training.attachFormer();
            System.out.println(training.create(training));


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}