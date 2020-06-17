package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = Exception.class)
    public void saveOverflow() throws Exception {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("fullName"));
            }
        } catch (Exception e) {
            Assert.fail("Exception not thrown");
        }
        storage.save(new Resume("Exception overflow"));
    }
}