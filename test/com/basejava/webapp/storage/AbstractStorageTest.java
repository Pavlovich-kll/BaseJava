package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\Users\\Kirill\\Documents\\GitHub\\basejava\\basejava\\storage");
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, "Grigoriy Kislin");
    private static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, "fullName2");
    private static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, "fullName3");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void save() throws Exception {
        Resume resume_4 = new Resume(UUID_4, "fullName4");
        storage.save(resume_4);
        assertSize(4);
        Assert.assertEquals(resume_4, storage.get(UUID_4));
    }


    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(UUID_1, "newFullName");
        storage.update(newResume);
        Assert.assertEquals(newResume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.get("Dummy");
    }

    @Test
    public void get() throws Exception {
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("Dummy");
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actualResume = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        assertSize(3);
        Assert.assertEquals(new ArrayList<>(expectedResumes), actualResume);
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}