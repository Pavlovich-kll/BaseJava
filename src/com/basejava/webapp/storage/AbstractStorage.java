package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SearchKey> implements Storage {

    public void save(Resume r) {
        LOG.info("Save " + r);
        SearchKey searchKey = getNotExistedKey(r.getUuid());
        doSave(r, searchKey);
    }

    public void update(Resume r) {
        LOG.info("Update " + r);
        SearchKey searchKey = getExistedKey(r.getUuid());
        doUpdate(r, searchKey);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SearchKey searchKey = getExistedKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SearchKey searchKey = getExistedKey(uuid);
        doDelete(searchKey);
    }

    private SearchKey getExistedKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKey getNotExistedKey(String uuid) {
        SearchKey searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("ERROR: Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getCopyAll();
        Collections.sort(list);
        return list;
    }

    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract List<Resume> getCopyAll();

    protected abstract void doSave(Resume r, SearchKey searchKey);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract void doUpdate(Resume r, SearchKey searchKey);

    protected abstract boolean isExist(SearchKey searchKey);

    protected abstract SearchKey getSearchKey(String uuid);

    protected abstract void doDelete(SearchKey searchKey);
}