package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> getAll();

    protected abstract void doSave(Resume resume, SK searchKey);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(Resume resume, SK searchKey);

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK getSearchKey(String uuid);

    protected abstract void doDelete(SK searchKey);

    @Override
    public void save(Resume resume) {
        LOG.info("Save " + resume);
        SK searchKey = getNotExistedKey(resume.getUuid());
        doSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        LOG.info("Update " + resume);
        Objects.requireNonNull(resume, "resume must not be null");
        SK searchKey = getExistedKey(resume.getUuid());
        doUpdate(resume, searchKey);

    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = getAll();
        resumes.sort(Resume::compareTo);
        return resumes;
    }

    private SK getExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}