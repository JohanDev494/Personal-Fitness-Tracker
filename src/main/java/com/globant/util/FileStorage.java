package com.globant.util;

import com.globant.model.WorkoutLog;

import java.io.*;
import java.util.List;

public class FileStorage {

    public static void saveLogs(List<WorkoutLog> logs, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(logs);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<WorkoutLog> loadLogs(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<WorkoutLog>) ois.readObject();
        }
    }
}

