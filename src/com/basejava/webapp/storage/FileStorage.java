package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.stream.SerializeStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final SerializeStrategy strategy;

    protected FileStorage(File directory, SerializeStrategy strategy) {
        Objects.requireNonNull(directory, " directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        for (File file : getFiles()) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return getFiles().length;
    }

    /**
     * directory - каталог, где мы будем сохранять наше резюме
     */

    @Override
    protected List<Resume> getAll() {
        List<Resume> list = new ArrayList<>(size());
        for (File file : getFiles()) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Can not save ", file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(Path.of(String.valueOf(file)))));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(Path.of(String.valueOf(file)))));
        } catch (IOException e) {
            throw new StorageException("Can not update ", file.getName(), e);
        }
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File not deleted", file.getName());
        }
    }

    private File[] getFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Error reading: list the files in a directory is null");
        }
        return files;
    }
}