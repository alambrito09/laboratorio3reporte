package org.principal.basededatos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:C:\\sqllite\\sqlite-tools-win-x64-3460100\\db_telebot";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("Conexión a SQLite establecida.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
