package com.globant.storage;

import java.util.List;

public interface Storage<T> {
    void save(List<T> data);
    List<T> load();
}
