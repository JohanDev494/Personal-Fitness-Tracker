package com.globant.storage.impl;

import com.globant.storage.Storage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonFileStorage<T> implements Storage<T> {
    private final String fileName;
    private final Gson gson;
    private final Type listType;

    public JsonFileStorage(String fileName, Class<T> clazz) {
        this.fileName = fileName;
        this.gson = new Gson();
        this.listType = TypeToken.getParameterized(List.class, clazz).getType();
    }

    @Override
    public void save(List<T> data) {
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar datos en archivo JSON", e);
        }
    }

    @Override
    public List<T> load() {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            List<T> data = gson.fromJson(reader, listType);
            return data != null ? data : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
