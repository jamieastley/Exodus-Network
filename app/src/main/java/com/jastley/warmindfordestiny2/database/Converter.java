package com.jastley.warmindfordestiny2.database;


import android.arch.persistence.room.TypeConverter;
import android.util.Log;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class Converter {

    @TypeConverter
    public static String fromStringSet(Set<String> strings) {
        if (strings==null) {
            return(null);
        }

        StringWriter result=new StringWriter();
        JsonWriter json=new JsonWriter(result);

        try {
            json.beginArray();

            for (String s : strings) {
                json.value(s);
            }

            json.endArray();
            json.close();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception creating JSON", e);
        }

        return(result.toString());
    }

    @TypeConverter
    public static Set<String> toStringSet(String strings) {
        if (strings==null) {
            return(null);
        }

        StringReader reader=new StringReader(strings);
        JsonReader json=new JsonReader(reader);
        HashSet<String> result=new HashSet<>();

        try {
            json.beginArray();

            while (json.hasNext()) {
                result.add(json.nextString());
            }

            json.endArray();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception parsing JSON", e);
        }

        return(result);
    }
}
