package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(Resume r, Object searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void doDelete(Object resume) {
        mapStorage.remove(((Resume) resume).getUuid());
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resume = new Resume[mapStorage.size()];
        mapStorage.values().toArray(resume);
        ArrayList<Resume> list = new ArrayList<>(Arrays.asList(resume));
        Collections.sort(list);
        return list.toArray(resume);
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}