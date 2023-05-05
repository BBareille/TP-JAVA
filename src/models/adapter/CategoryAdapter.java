package models.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import database.SqlConnection;
import models.Category;
import models.Level;

import java.io.IOException;

public class CategoryAdapter extends TypeAdapter<Category> {
    SqlConnection connection;
    public CategoryAdapter(SqlConnection connection){
        this.connection = connection;
    }
    @Override
    public void write(JsonWriter jsonWriter, Category category) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(category.getId());
        jsonWriter.name("name");
        jsonWriter.value(category.getName());
        jsonWriter.endObject();
    }

    @Override
    public Category read(JsonReader jsonReader) throws IOException {
        Category category = new Category(connection);
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
                category.setId(jsonReader.nextLong());
            }

            if("name".equals(fieldname)) {
                token = jsonReader.peek();
                category.setName(jsonReader.nextString());
            }



        }
        jsonReader.endObject();
        return category;
    }
}
