package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract Integer getSearchKey(String uuid);

    protected abstract void shiftAfterDeletion(int index);

    protected abstract void insertResume(int index, Resume resume);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int size() {
        return size;
    }

    @Override
    protected void doSave(Resume resume, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("ERROR: Количество резюме превысило допустимое значение", resume.getUuid());
        } else {
            insertResume(index, resume);
            size++;
        }
    }

    @Override
    protected void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected void doDelete(Integer index) {
        shiftAfterDeletion(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}