package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Резюме " + r.getUuid() + "  уже существует");
        } else if (STORAGE_LIMIT <= size) {
            System.out.println("ERROR: Количество резюме превысило допустимое значение");
        } else {
            insertValue(index, r);
            size++;
        }
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Резюме " + r.getUuid() + " отсутсвует");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            deleteValue(index);
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Резюме " + uuid + " отсутсвует, удаление невозможно");
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

    protected abstract void deleteValue(int index);

    protected abstract void insertValue(int index, Resume r);
}