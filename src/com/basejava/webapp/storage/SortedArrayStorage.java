package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertValue(int index, Resume r) {
        int insertNum = size;
        System.arraycopy(storage, insertNum + 1, storage, insertNum, 1);
        storage[insertNum] = r;
    }

    @Override
    protected void deleteValue(int index) {
        int numShiftedValues = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, numShiftedValues);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}