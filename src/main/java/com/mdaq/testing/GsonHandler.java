package com.mdaq.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public class GsonHandler {

    private static Gson gson;

    private static Gson nonNullGson;

    public static Gson getNonNullGson(){
        if(nonNullGson == null){
            nonNullGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().serializeNulls().create();
        }
        return nonNullGson;
    }

    public static Gson getGson(){
        if(gson == null){
            gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        }
        return gson;
    }
}
