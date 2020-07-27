package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getProperties().getProperty("db.url"),
                Config.get().getProperties().getProperty("db.user"),
                Config.get().getProperties().getProperty("db.password")));
    }
}