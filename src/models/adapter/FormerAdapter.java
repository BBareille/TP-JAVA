package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import database.SqlConnection;
import models.Former;
import models.Level;

import java.io.IOException;

public class FormerAdapter extends TypeAdapter<Former> {

    SqlConnection connection;
    public FormerAdapter(SqlConnection connection){
        this.connection = connection;
    }
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
        Former former = new Former(connection);
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
                former.setId(jsonReader.nextLong());
            }

            if("firstName".equals(fieldname)) {
                token = jsonReader.peek();
                former.setFirstName(jsonReader.nextString());
            }

            if("lastName".equals(fieldname)){
                token = jsonReader.peek();
                former.setLastName(jsonReader.nextString());
            }


        }
        jsonReader.endObject();
        return former;
    }
}
