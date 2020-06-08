package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void save(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        } else if (size >= STORAGE_LIMIT) {
            throw new StorageException("ERROR: Количество резюме превысило допустимое значение", uuid);
        } else {
            insertNewResume(index, r);
            size++;
        }
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = r;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            shiftAfterDeletion(index);
            storage[size - 1] = null;
            size--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void shiftAfterDeletion(int index);

    protected abstract void insertNewResume(int index, Resume r);
}