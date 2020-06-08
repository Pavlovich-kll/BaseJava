package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void save() throws Exception {
        Resume newResume = new Resume(UUID_4);
        storage.save(newResume);
        Assert.assertEquals(4, storage.size());
        assertNewResume(newResume);

    }

    @Test(expected = Exception.class)
    public void saveOverFlow() throws Exception {
        try {
            for (int i = 4; i <= 10000; i++) {
                storage.save(new Resume());
            }
        } catch(Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        Assert.assertSame(UUID_1, storage.get(UUID_1));
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() throws Exception {
        storage.getAll();
        Assert.assertEquals(3, storage.size());

    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    private void assertNewResume(Resume r) {
        Assert.assertEquals(r, storage.get(r.getUuid()));
    }
}
