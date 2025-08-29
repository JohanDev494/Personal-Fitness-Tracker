package com.globant.storage.impl;

import com.globant.storage.Storage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileStorage<T> implements Storage<T> {
    private final String fileName;

    public FileStorage(String fileName) {
        this.fileName = fileName;
//        this.fileName = "data/" + fileName;
//        File dir = new File("data");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }
    }


    @Override
    public void save(List<T> data) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar datos en archivo", e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> load() {
        File file = new File(fileName);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}

