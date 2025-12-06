package br.com.projeto.bap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionFactory {
    

    // Configurações do Banco do Docker (verifique se a senha é admin123 ou a que definiu)
    private static final String URL = "jdbc:postgresql://localhost:5432/catalogo_db";
    private static final String USER = "catalogo_user";
    private static final String PASS = "admin123"; 

    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver explicitamente
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não encontrado", e);
        }
    }
}
