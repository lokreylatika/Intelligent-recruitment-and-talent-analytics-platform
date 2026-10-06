package com.recruitment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/Recruitment_db";

    private static final String USER =
            "postgres";

    private static final String PASSWORD =
            "2510080043";

    public static Connection getConnection()
            throws SQLException {

        try {

            Class.forName("org.postgresql.Driver");

        } catch (ClassNotFoundException e) {

            throw new SQLException(
                "PostgreSQL JDBC Driver not found",
                e
            );
        }

        return DriverManager.getConnection(
            URL,
            USER,
            PASSWORD
        );
    }
}