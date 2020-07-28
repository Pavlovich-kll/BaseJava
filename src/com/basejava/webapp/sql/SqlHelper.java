package com.basejava.webapp.sql;

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

    public void prepare(String sqlCommand) {
        prepare(sqlCommand, PreparedStatement::execute);
    }

    public <T> T prepare(String sqlCommand, RequestSQLProcessing<T> requestSQLProcessing) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sqlCommand)) {
            return requestSQLProcessing.request(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}