package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage {
    private final HashMap<String, Resume> myHashMap = new HashMap<String, Resume>();

    @Override
    protected void doSave(Resume r, Object searchKey) {
        myHashMap.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void doUpdate(Resume r, Object resume) {
        myHashMap.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object resume) {
        return resume != null;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return myHashMap.get(uuid);
    }

    @Override
    protected void doDelete(Object resume) {
        myHashMap.remove(((Resume) resume).getUuid());
    }

    @Override
    public void clear() {
        myHashMap.clear();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resume = new Resume[myHashMap.size()];
        myHashMap.values().toArray(resume);
        return resume;
    }

    @Override
    public int size() {
        return myHashMap.size();
    }
}