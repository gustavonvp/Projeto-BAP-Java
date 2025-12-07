package br.com.projeto.bap.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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



    private static final String SQL_LIST_ALL = "SELECT * FROM T_PESSOA ORDER BY nome_completo";

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pessoa p = new Pessoa();
                p.setId(rs.getLong("id"));
                p.setNomeCompleto(rs.getString("nome_completo"));
                p.setBiografia(rs.getString("biografia"));
                
                // Conversão segura de data
                Date dataSql = rs.getDate("data_nascimento");
                if (dataSql != null) {
                    p.setDataNascimento(dataSql.toLocalDate());
                }
                
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pessoas", e);
        }
        return lista;
    }
}
