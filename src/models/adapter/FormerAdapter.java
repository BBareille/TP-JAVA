package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.Former;

import java.io.IOException;

public class FormerAdapter extends TypeAdapter<Former> {

    @Override
    public void write(JsonWriter jsonWriter, Former former) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(former.getId());
        jsonWriter.name("firstName");
        jsonWriter.value(former.getFirstName());
        jsonWriter.name("lastName");
        jsonWriter.value(former.getLastName());
        jsonWriter.endObject();
    }

    @Override
    public Former read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
