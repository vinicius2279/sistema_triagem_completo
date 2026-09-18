package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/triagemdb",
                "root",
                "admin"
        );

        return connection;
    }
}

