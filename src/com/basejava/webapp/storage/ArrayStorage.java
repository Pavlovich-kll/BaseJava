package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void save(Resume r) {
        int index = getInd(r.getUuid());
        if (index >= 0) {
            System.out.println("ERROR: Резюме " + r.getUuid() + "  уже существует");
        } else if(index >= size) {
            System.out.println("ERROR: Количество резюме превысило допустимое значение");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        int index = getInd(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("ERROR: Резюме " + r.getUuid() + " отсутсвует");
        }
    }

    public Resume get(String uuid) {
        int index = getInd(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    private int getInd(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        int index = getInd(uuid);
        if (index >= 0) {
            storage[index] = storage[size - 1];
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
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
