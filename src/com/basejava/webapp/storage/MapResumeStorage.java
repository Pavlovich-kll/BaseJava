package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final HashMap<String, Resume> mapStorage = new HashMap<>();

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doUpdate(Resume r, Resume resume) {
        mapStorage.put(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return resume != null;
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return mapStorage.get(uuid);
    }

    @Override
    protected void doDelete(Resume resume) {
        mapStorage.remove(resume.getUuid());
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }
}