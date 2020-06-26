package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume resume_1 = ResumeTestData.getResume1("uuid1", "Grigoriy Kislin");
    private static final Resume resume_2 = ResumeTestData.getResume2("uuid2", "fullName2");
    private static final Resume resume_3 = ResumeTestData.getResume3("uuid3", "fullName3");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
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
        Assert.assertEquals(resume_1, storage.get(UUID_1));
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
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() throws Exception {
        List<Resume> actualResume = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(resume_1, resume_2, resume_3);
        assertSize(3);
        Assert.assertEquals(expectedResumes, actualResume);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    public void assertSize(int size) {
        Assert.assertEquals(size, storage.size());
    }
}