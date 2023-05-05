package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import database.SqlConnection;
import models.Trainee;
import models.Training;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

public class TrainingAdapter extends TypeAdapter<Training> {

    SqlConnection connection;
    public TrainingAdapter(SqlConnection connection){
        this.connection = connection;
    }
    @Override
    public void write(JsonWriter jsonWriter, Training training) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(training.getId());
        jsonWriter.name("name");
        jsonWriter.value(training.getName());
        jsonWriter.name("start_at");
        jsonWriter.value(training.getStart_at().toString());
        jsonWriter.name("duration");
        jsonWriter.value(training.getDuration());
        jsonWriter.name("price");
        jsonWriter.value(training.getPrice());
        jsonWriter.name("online");
        jsonWriter.value(training.isOnline());

        try {

            jsonWriter.name("level");
                jsonWriter.beginObject();
                jsonWriter.name("id");
                jsonWriter.value(training.getLevel().getId());
                jsonWriter.name("name");
                jsonWriter.value(training.getLevel().getName());
            jsonWriter.endObject();

            jsonWriter.name("category");
            jsonWriter.beginObject();
                jsonWriter.name("id");
                jsonWriter.value(training.getCategory().getId());
                jsonWriter.name("name");
                jsonWriter.value(training.getCategory().getName());
            jsonWriter.endObject();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        jsonWriter.endObject();
    }

    @Override
    public Training read(JsonReader jsonReader) throws IOException {
        Training training = new Training(connection);
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
                training.setId(jsonReader.nextLong());
            }

            if("name".equals(fieldname)) {
                token = jsonReader.peek();
                training.setName(jsonReader.nextString());
            }

            if("start_at".equals(fieldname)){
                token = jsonReader.peek();
                try {
                    training.setStart_at(jsonReader.nextString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
            if("duration".equals(fieldname)){
                token = jsonReader.peek();
                training.setDuration(jsonReader.nextInt());
            }
            if("price".equals(fieldname)){
                token = jsonReader.peek();
                training.setPrice(jsonReader.nextInt());
            }
            if("online".equals(fieldname)){
                token = jsonReader.peek();
                training.setOnline(jsonReader.nextBoolean());
            }
            if("level_id".equals(fieldname)){
                token = jsonReader.peek();
                training.setLevel(jsonReader.nextLong());
            }
            if("category_id".equals(fieldname)){
                token = jsonReader.peek();
                training.setCategory(jsonReader.nextLong());
            }

        }
        jsonReader.endObject();
        return training;
    }
}
