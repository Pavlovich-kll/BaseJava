package com.basejava.webapp.storage;

import com.basejava.webapp.storage.serializer.SerializeStrategyObject;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new SerializeStrategyObject()));
    }
}