package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void execute(String sqlCommand) {
        execute(sqlCommand, PreparedStatement::execute);
    }

    public <T> T execute(String sqlCommand, SqlProcessor<T> sqlProcessor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return sqlProcessor.process(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) throw new ExistStorageException("error of unique violation");
            else {
                throw new StorageException(e);
            }
        }
    }
}