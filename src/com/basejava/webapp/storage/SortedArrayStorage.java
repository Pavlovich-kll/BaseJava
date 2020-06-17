package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

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
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid,"Dummy" );
        return Arrays.binarySearch(storage, 0, size, searchKey,RESUME_COMPARATOR);
    }
    /**
     * class ResumeComparator implements Comparator<Resume>{
     * @Override public int compare(Resume o1, Resume o2) {
     * return o1.getUuid().compareTo(o2.getUuid());
     * }
     * }
     *
     * Рефакторим в лямбду -->
     *
     * Лямбда:
     * private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
     * }
     */

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);
}
