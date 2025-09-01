package com.globant.storage.impl;

import com.globant.storage.Storage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonFileStorage<T> implements Storage<T> {
    private final String filePath;
    private final Gson gson;
    private final Type listType;

    public JsonFileStorage(String fileName, Class<T> clazz) {
        // Aseguramos que los archivos vayan a la carpeta "data"
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs(); // crea la carpeta si no existe
        }

        this.filePath = "data" + File.separator + fileName;
        this.gson = new Gson();
        this.listType = TypeToken.getParameterized(List.class, clazz).getType();
    }

    @Override
    public void save(List<T> data) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar datos en archivo JSON", e);
        }
    }

    @Override
    public List<T> load() {
        File file = new File(filePath);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
