package br.com.projeto.bap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // URL aponta para o seu próprio PC (localhost)
    private static final String URL = "jdbc:postgresql://localhost:5432/catalogo_db";
    
    // O usuário padrão do Postgres no Windows costuma ser 'postgres'
    private static final String USER = "postgres"; 
    
    // ⚠️ COLOQUE AQUI A SUA SENHA DO PGADMIN ⚠️
    private static final String PASS = "postgress"; // <-- Mude isso!

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC não encontrado", e);
        }
    }
}