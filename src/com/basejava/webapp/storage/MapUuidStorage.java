package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage {
    private final HashMap<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    protected void doSave(Resume r, Object uuid) {
        mapStorage.put((String) uuid, r);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return mapStorage.get((String) uuid);
    }

    @Override
    protected void doUpdate(Resume r, Object uuid) {
        mapStorage.put((String) uuid, r);
    }

    @Override
    protected boolean isExist(Object uuid) {
        return mapStorage.containsKey((String) uuid);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doDelete(Object uuid) {
        mapStorage.remove((String) uuid);
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}
