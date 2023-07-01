package com.mdaq.testing;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {

    public static <T> T jsonFileToObject(String filePath, Class<T> clazz) throws FileNotFoundException {
        return jsonFileToObject(filePath, clazz, GsonHandler.getGson());
    }

    public static <T> T jsonFileToObject(String filePath, Class<T> clazz, Gson gson) throws FileNotFoundException {
        return gson.fromJson(getFileReader(filePath), clazz);
    }

    public static Reader getFileReader(String filePath) throws FileNotFoundException {
        return new FileReader(getFileDirectory(
                filePath).toFile());
    }

    public static Path getFileDirectory(String pathFromSource) {
        return Paths.get(System.getProperty("user.dir"), pathFromSource);
    }
}
