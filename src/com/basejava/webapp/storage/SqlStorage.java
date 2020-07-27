package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public int size() {
        return prepare("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public void clear() {
        sqlHelper.prepare("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        prepare("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();//Adds a set of parameters to this object's batch of commands
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        prepare("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {//Sets the designated parameter to SQL for INSERT, UPDATE or DELETE;
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return prepare("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);//передаем на знак "?" параметр uuid;
            ResultSet rs = ps.executeQuery();//получаем uuid; ResultSet object that contains the data produced by the query; never nul
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        prepare("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {//because DELETE
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return prepare("SELECT * FROM resume ORDER by uuid, full_name", ps -> {
            ResultSet rs = ps.executeQuery();//like usual "get"
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        });
    }

    @FunctionalInterface
    private interface ElementPst<T> {
        T prepare(PreparedStatement ps) throws IOException, SQLException;
    }

    public <T> T prepare(String pst, ElementPst<T> elementPst) {
        try (Connection conn = sqlHelper.connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(pst)) {
            return elementPst.prepare(ps);
        } catch (SQLException | IOException e) {
            throw new StorageException(e);
        }
    }
}