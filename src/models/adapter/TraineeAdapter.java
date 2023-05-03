package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.Trainee;

import java.io.IOException;

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
        return null;
    }
}
