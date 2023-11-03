package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://<URL>:3306/seubanco";
    private static final String USUARIO = "<USUÁRIO>";
    private static final String SENHA = "<SENHA>";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
