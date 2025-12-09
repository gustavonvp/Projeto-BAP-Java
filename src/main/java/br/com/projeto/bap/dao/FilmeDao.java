package br.com.projeto.bap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.bap.model.Filme;
import br.com.projeto.bap.util.ConnectionFactory;

public class FilmeDao{

    private static final String SQL_INSERT = 
        "INSERT INTO T_FILME (titulo, ano, genero, duracao_minutos, sinopse, capa_url, trailer_url) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_LIST = "SELECT * FROM T_FILME ORDER BY titulo";

    // INSERT DE VINCULO (Para simplificar, vamos salvar sem o 'papel' por enquanto)
    private static final String SQL_INSERT_ELENCO = "INSERT INTO T_FILME_ELENCO (id_filme, id_pessoa) VALUES (?, ?)";

    public void salvar(Filme filme, List<Long> idsEquipe) {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmtVinculo = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Transação

            stmt = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, filme.getTitulo());
            
            if(filme.getAno() != null) stmt.setInt(2, filme.getAno()); 
            else stmt.setNull(2, Types.INTEGER);
            
            stmt.setString(3, filme.getGenero());
            
            if(filme.getDuracaoMinutos() != null) stmt.setInt(4, filme.getDuracaoMinutos()); 
            else stmt.setNull(4, Types.INTEGER);
            
            stmt.setString(5, filme.getSinopse());
            stmt.setString(6, filme.getCapaUrl());
            stmt.setString(7, filme.getTrailerUrl());
            
            stmt.executeUpdate();

            // Recuperar ID
            Long idGerado = null;
            try(ResultSet rs = stmt.getGeneratedKeys()){
                if(rs.next()) idGerado = rs.getLong(1);
            }
            filme.setId(idGerado);

            // Salvar Elenco (Diretor/Atores)
            if (idGerado != null && idsEquipe != null && !idsEquipe.isEmpty()) {
                stmtVinculo = conn.prepareStatement(SQL_INSERT_ELENCO);
                for (Long idPessoa : idsEquipe) {
                    stmtVinculo.setLong(1, idGerado);
                    stmtVinculo.setLong(2, idPessoa);
                    stmtVinculo.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            try { if(conn!=null) conn.rollback(); } catch(SQLException ex) {}
            throw new RuntimeException("Erro ao salvar filme", e);
        } finally {
            // Feche os recursos...
            try { if(conn!=null) conn.close(); } catch(SQLException ex) {}
        }
    }

    public List<Filme> listarTodos() {
        List<Filme> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LIST);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Filme f = new Filme();
                f.setId(rs.getLong("id"));
                f.setTitulo(rs.getString("titulo"));
                f.setAno(rs.getInt("ano"));
                f.setGenero(rs.getString("genero"));
                f.setDuracaoMinutos(rs.getInt("duracao_minutos"));
                f.setCapaUrl(rs.getString("capa_url"));
                lista.add(f);
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return lista;
    }
}