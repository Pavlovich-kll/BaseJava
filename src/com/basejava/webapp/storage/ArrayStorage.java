package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    private static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    protected void insertValue(int index, Resume r) {
        storage[size] = r;
    }

    protected void deleteValue(int index) {
        storage[index] = storage[size - 1];
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}