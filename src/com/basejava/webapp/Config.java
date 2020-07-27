package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected final File PROPS = new File("config\\\\resumes.properties");
    private final Properties properties = new Properties();
    private final File storageDir;

    public static Config get() {
        return INSTANCE;
    }

    public Properties getProperties() {
        return properties;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            properties.load(is);//выявляем ошибки на старте приложения
            storageDir = new File(properties.getProperty("storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}