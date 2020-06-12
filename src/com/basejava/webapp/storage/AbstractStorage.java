package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume r) {
        Object searchKey = ifKeyNotExist(r.getUuid());
        doSave(r, searchKey);
    }

    public void update(Resume r) {
        Object searchKey = ifKeyExist(r.getUuid());
        doUpdate(r, searchKey);
    }

    public Resume get(String uuid) {
        Object searchKey = ifKeyExist(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = ifKeyExist(uuid);
        doDelete(searchKey);
    }

    private Object ifKeyExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object ifKeyNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doSave(Resume r, Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doDelete(Object searchKey);

}