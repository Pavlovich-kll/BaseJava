package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapUuidStorage extends AbstractStorage<String> {
    private final HashMap<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        mapStorage.put(uuid, r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, String uuid) {
        mapStorage.put(uuid, r);
    }

    @Override
    protected boolean isExist(String uuid) {
        return mapStorage.containsKey(uuid);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doDelete(String uuid) {
        mapStorage.remove(uuid);
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
