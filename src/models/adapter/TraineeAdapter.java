package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import database.SqlConnection;
import models.Trainee;

import java.io.IOException;
import java.sql.SQLException;

public class TraineeAdapter extends TypeAdapter<Trainee> {
    @Override
    public void write(JsonWriter jsonWriter, Trainee trainee) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(trainee.getId());
        jsonWriter.name("firstName");
        jsonWriter.value(trainee.getFirstName());
        jsonWriter.name("lastName");
        jsonWriter.value(trainee.getLastName());
        jsonWriter.endObject();
    }

    @Override
    public Trainee read(JsonReader jsonReader) throws IOException {
        SqlConnection connection = new SqlConnection();
        try {
            connection.connect();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Trainee trainee = new Trainee(connection);
        jsonReader.beginObject();
        String fieldname = null;

        while(jsonReader.hasNext()){
            JsonToken token = jsonReader.peek();

            if (token.equals(JsonToken.NAME)) {
                //get the current token
                fieldname = jsonReader.nextName();
            }

            if("id".equals(fieldname)){
                token = jsonReader.peek();
                trainee.setId(jsonReader.nextLong());
            }

            if("firstName".equals(fieldname)) {
                token = jsonReader.peek();
                trainee.setFirstName(jsonReader.nextString());
            }

            if("lastName".equals(fieldname)){
                token = jsonReader.peek();
                trainee.setLastName(jsonReader.nextString());
            }



        }
        jsonReader.endObject();
        return trainee;
    }
}
