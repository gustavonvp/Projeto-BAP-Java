package br.com.projeto.bap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import br.com.projeto.bap.model.Livro;
import br.com.projeto.bap.util.ConnectionFactory;

public class LivroDao {

    private static final String SQL_INSERT_LIVRO = 
        "INSERT INTO T_LIVRO (titulo, editora, isbn, ano, genero, sinopse) VALUES (?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_INSERT_AUTORES = 
        "INSERT INTO T_OBRA_AUTORES (id_livro, id_pessoa) VALUES (?, ?)";

    public void salvar(Livro livro, List<Long> idsAutores) {
        Connection conn = null;
        PreparedStatement stmtLivro = null;
        PreparedStatement stmtVinculo = null;

        try {
            conn = ConnectionFactory.getConnection();
            
            // 1. Desliga o Auto-Commit para iniciar uma TRANSAÇÃO
            // Isso garante que tudo seja salvo junto, ou nada seja salvo.
            conn.setAutoCommit(false);

            // 2. Salva o Livro
            // O parâmetro RETURN_GENERATED_KEYS pede ao banco o ID criado
            stmtLivro = conn.prepareStatement(SQL_INSERT_LIVRO, Statement.RETURN_GENERATED_KEYS);
            stmtLivro.setString(1, livro.getTitulo());
            stmtLivro.setString(2, livro.getEditora());
            stmtLivro.setString(3, livro.getIsbn());
            // Trata Inteiros nulos
            if (livro.getAno() != null) stmtLivro.setInt(4, livro.getAno()); else stmtLivro.setNull(4, java.sql.Types.INTEGER);
            stmtLivro.setString(5, livro.getGenero());
            stmtLivro.setString(6, livro.getSinopse());
            
            stmtLivro.executeUpdate();

            // 3. Recupera o ID gerado para o Livro
            Long idLivroGerado = null;
            try (ResultSet rs = stmtLivro.getGeneratedKeys()) {
                if (rs.next()) {
                    idLivroGerado = rs.getLong(1);
                    livro.setId(idLivroGerado);
                }
            }

            // 4. Salva os Autores na tabela de junção (Se houver autores selecionados)
            if (idLivroGerado != null && idsAutores != null && !idsAutores.isEmpty()) {
                stmtVinculo = conn.prepareStatement(SQL_INSERT_AUTORES);
                
                for (Long idAutor : idsAutores) {
                    stmtVinculo.setLong(1, idLivroGerado);
                    stmtVinculo.setLong(2, idAutor);
                    stmtVinculo.executeUpdate(); // Adiciona cada par (Livro, Autor)
                }
            }

            // 5. Confirma a transação (Commit)
            conn.commit();
            System.out.println("Livro e autores salvos com sucesso!");

        } catch (SQLException e) {
            // Se der erro, desfaz tudo (Rollback)
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao salvar livro: " + e.getMessage(), e);
        } finally {
            // Fecha recursos manualmente (bloco finally clássico do JDBC)
            try {
                if (stmtLivro != null) stmtLivro.close();
                if (stmtVinculo != null) stmtVinculo.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}