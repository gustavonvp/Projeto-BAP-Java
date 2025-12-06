package br.com.projeto.bap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.projeto.bap.model.Pessoa;
import br.com.projeto.bap.util.ConnectionFactory;

public class PessoaDao {
    
    // SQL com '?' para evitar injection [cite: 305]
    private static final String SQL_INSERT = "INSERT INTO T_PESSOA (nome_completo, biografia, data_nascimento) VALUES (?, ?, ?)";

    public void salvar(Pessoa pessoa) {
        // Try-with-resources fecha a conexão automaticamente [cite: 306]
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, pessoa.getNomeCompleto());
            stmt.setString(2, pessoa.getBiografia());
            stmt.setDate(3, java.sql.Date.valueOf(pessoa.getDataNascimento()));

            stmt.executeUpdate();
            System.out.println("Pessoa salva com sucesso!");

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pessoa: " + e.getMessage(), e);
        }
    }
}
