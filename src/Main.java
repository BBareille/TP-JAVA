import Console.Console;
import database.SqlConnection;

public class Main {
    public static void main(String[] args){
        SqlConnection connection = new SqlConnection();
        try {
            connection.connect();

//
//            Category category = new Category(connection);
//            category.setName("Développement");
//            category.create(category);
//
//            Level level = new Level(connection);
//            level.setName("Medium");
//            level.create(level);
//
//
//            Training training = new Training(connection);
//            training.setName("NodeJS");
//            training.setStart_at(new Date());
//            training.setCategory(3L);
//            training.setDuration(6);
//            training.setPrice(1500);
//            training.setOnline(true);
//            training.setLevel(level.getId());

//            Former former = new Former();
//            former.setFirstName("Michel");
//            former.setLastName("Obama");
//            former.create(former);
//            ArrayList<Former> formers = new ArrayList<>();
//            formers.add(former);
//            training.attachFormer(formers);
//            System.out.println(training.create(training));

           Console console = new Console();
           console.run();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}