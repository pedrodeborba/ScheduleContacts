package com.example.schedulecontacts.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/ScheduleContacts";
    private static final String USUARIO = "pedrodeborba";
    private static final String SENHA = "12345678";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}