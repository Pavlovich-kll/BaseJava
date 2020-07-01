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
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final SerializeStrategy strategy;

    protected PathStorage(File dir, SerializeStrategy strategy) {
        directory = Paths.get(String.valueOf(dir));
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory");
        }
        this.strategy = strategy;
    }

    @Override
    public void clear() {
        getDirectoryFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getDirectoryFiles().count();
    }

    /**
     * directory - каталог, где мы будем сохранять наше резюме
     */

    @Override
    protected List<Resume> getAll() {
        return getDirectoryFiles().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Can not save path ", getFileName(path), e);
        }
        doUpdate(resume, path);
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", getFileName(path), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            strategy.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Can not update ", resume.getUuid(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path not deleted", getFileName(path));
        }
    }

    private Stream<Path> getDirectoryFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("error of directory", e);
        }
    }

    private String getFileName(Path path) {
        return path.getFileName().toString();
    }
}