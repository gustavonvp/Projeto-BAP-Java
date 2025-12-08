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
    private static final String SQL_INSERT = 
        "INSERT INTO T_PESSOA (nome_completo, biografia, data_nascimento, foto_url) VALUES (?, ?, ?, ?)";

    public void salvar(Pessoa pessoa) {
        // Try-with-resources fecha a conexão automaticamente [cite: 306]
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_INSERT)) {

            stmt.setString(1, pessoa.getNomeCompleto());
            stmt.setString(2, pessoa.getBiografia());
            stmt.setDate(3, java.sql.Date.valueOf(pessoa.getDataNascimento()));
            stmt.setString(4, pessoa.getFotoUrl());
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
                p.setFotoUrl(rs.getString("foto_url"));
                lista.add(p);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pessoas", e);
        }
        return lista;
    }



    private static final String SQL_UPDATE = 
        "UPDATE T_PESSOA SET nome_completo=?, biografia=?, data_nascimento=?, foto_url=? WHERE id=?";    private static final String SQL_DELETE = "DELETE FROM T_PESSOA WHERE id=?";
    private static final String SQL_SELECT_ID = "SELECT * FROM T_PESSOA WHERE id=?";
    // Para excluir pessoa, precisamos limpar os vínculos dela com livros primeiro
    private static final String SQL_DELETE_VINCULOS_PESSOA = "DELETE FROM T_OBRA_AUTORES WHERE id_pessoa=?";

    public Pessoa buscarPorId(Long id) {
        Pessoa p = null;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_SELECT_ID)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new Pessoa();
                    p.setId(rs.getLong("id"));
                    p.setNomeCompleto(rs.getString("nome_completo"));
                    p.setBiografia(rs.getString("biografia"));
                    Date dataSql = rs.getDate("data_nascimento");
                    if (dataSql != null) p.setDataNascimento(dataSql.toLocalDate());
                    p.setFotoUrl(rs.getString("foto_url"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa", e);
        }
        return p;
    }

    public void atualizar(Pessoa pessoa) {
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_UPDATE)) {
            
            stmt.setString(1, pessoa.getNomeCompleto());
            stmt.setString(2, pessoa.getBiografia());
            stmt.setDate(3, pessoa.getDataNascimento() != null ? Date.valueOf(pessoa.getDataNascimento()) : null);
            stmt.setString(4, pessoa.getFotoUrl());
            stmt.setLong(5, pessoa.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pessoa", e);
        }
    }

    public void excluir(Long id) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            conn.setAutoCommit(false); // Transação para garantir limpeza
            
            // 1. Remove vínculos com livros (T_OBRA_AUTORES)
            try (PreparedStatement stmtVinc = conn.prepareStatement(SQL_DELETE_VINCULOS_PESSOA)) {
                stmtVinc.setLong(1, id);
                stmtVinc.executeUpdate();
            }

            // 2. Remove a Pessoa
            try (PreparedStatement stmtPessoa = conn.prepareStatement(SQL_DELETE)) {
                stmtPessoa.setLong(1, id);
                stmtPessoa.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pessoa", e);
        }
    }

    private Pessoa mapearPessoa(ResultSet rs) throws SQLException {
        Pessoa p = new Pessoa();
        p.setId(rs.getLong("id"));
        p.setNomeCompleto(rs.getString("nome_completo"));
        p.setBiografia(rs.getString("biografia"));
        p.setFotoUrl(rs.getString("foto_url")); // <--- Lendo do banco
        Date dt = rs.getDate("data_nascimento");
        if(dt != null) p.setDataNascimento(dt.toLocalDate());
        return p;
    }

    // Novo SQL de Busca
    private static final String SQL_BUSCA = 
        "SELECT * FROM T_PESSOA WHERE LOWER(nome_completo) LIKE LOWER(?) ORDER BY nome_completo";

        public List<Pessoa> buscarPorTermo(String termo) {
        List<Pessoa> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCA)) {
            
            stmt.setString(1, "%" + termo + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Pessoa p = mapearPessoa(rs); // Extrair lógica se quiser, ou repetir o código
                    lista.add(p);
                }
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }


}
