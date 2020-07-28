package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

public interface Storage {

    int size();

    void clear();

    void save(Resume resume);

    void update(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();
}