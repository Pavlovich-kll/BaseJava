package com.basejava.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface RequestSQLProcessing<T> {
    T request(PreparedStatement ps) throws SQLException;
}