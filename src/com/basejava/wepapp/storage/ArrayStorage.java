package com.basejava.wepapp.storage;

import com.basejava.wepapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void save(Resume r) {
        //TODO check if resume not present
        int i = getInd(r.getUuid());
        if (i >= 0) {
            System.out.println("ERROR: Резюме уже существует");
        } else if(i >= size) {
            System.out.println("ERROR: Количество резюме превысило допустимое значение");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void update(Resume r) {
        //TODO check if resume present
        int i = getInd(r.getUuid());
        if (i >= 0) {
            storage[i] = r;
        } else {
            System.out.println("ERROR: Резюме отсутсвует");
        }
    }

    public Resume get(String uuid) {
        //TODO check if resume present
        int i = getInd(uuid);
        if (i >= 0) {
            return storage[i];
        } else {
            System.out.println("ERROR: Резюме отсутсвует");
        }
        return null;
    }

    /**
     * Special method of searching resume
     */
    public int getInd(String uuid) {
        for (int ind = 0; ind < size; ind++) {
            if (storage[ind].getUuid().equals(uuid)) {
                return ind;
            }
        }
        return -1;
    }

    public void delete(String uuid) {
        //TODO check if resume exist
        int i = getInd(uuid);
        if (i >= 0) {
            storage[i] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        } else {
            System.out.println("ERROR: Резюме отсутсвует, удаление невозможно");
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
