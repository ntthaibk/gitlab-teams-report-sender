package com.mdaq.testing;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.http.HttpClient;

public class GsonHandler {

    private static Gson gson;

    public static Gson getGson(){
        if(gson == null){
            gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        }
        return gson;
    }
}
