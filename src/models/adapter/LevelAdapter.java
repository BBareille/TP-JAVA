package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import database.SqlConnection;
import models.Level;

import java.io.IOException;

public class LevelAdapter extends TypeAdapter<Level> {

    SqlConnection connection;
    public LevelAdapter(SqlConnection connection){
        this.connection = connection;
    }
    @Override
    public void write(JsonWriter jsonWriter, Level level) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(level.getId());
        jsonWriter.name("name");
        jsonWriter.value(level.getName());
        jsonWriter.endObject();
    }

    @Override
    public Level read(JsonReader jsonReader) throws IOException {
        Level level = new Level(connection);
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
                level.setId(jsonReader.nextLong());
            }

            if("name".equals(fieldname)) {
                token = jsonReader.peek();
                level.setName(jsonReader.nextString());
            }



        }
        jsonReader.endObject();
        return level;
    }
}
