package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

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
        return sqlHelper.prepare("SELECT COUNT(*) FROM resume", ps -> {
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
        sqlHelper.prepare("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            try {
                ps.execute();//Adds a set of parameters to this object's batch of commands
            } catch (SQLException e) {
                if (e.getSQLState() != null) throw new ExistStorageException(resume.getUuid());
                else throw new SQLException(e);//getSQLState return the vendor's error code;
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.prepare("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
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
        return sqlHelper.prepare("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
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
        sqlHelper.prepare("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {//because DELETE
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.prepare("SELECT * FROM resume ORDER BY uuid, full_name", ps -> {
            ResultSet rs = ps.executeQuery();//like usual "get"
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }
}