package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertNewResume(int index, Resume r) {
        int insertNum = -index - 1;
        System.arraycopy(storage, insertNum, storage, insertNum + 1, size - insertNum);
        storage[insertNum] = r;
    }

    @Override
    protected void shiftAfterDeletion(int index) {
        int numShiftedValues = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, numShiftedValues);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}