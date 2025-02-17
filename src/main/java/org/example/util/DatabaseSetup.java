package org.example.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void setupDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            String createTable = """
                    CREATE TABLE IF NOT EXISTS funcionarios (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nome VARCHAR(100) NOT NULL,
                        data_nascimento DATE NOT NULL,
                        salario DECIMAL(10,2) NOT NULL,
                        funcao VARCHAR(100) NOT NULL);
                    """;
            stmt.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
