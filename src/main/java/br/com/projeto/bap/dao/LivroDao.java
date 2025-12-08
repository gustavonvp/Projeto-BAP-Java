package br.com.projeto.bap.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.bap.model.Livro;
import br.com.projeto.bap.util.ConnectionFactory;


/**
 * Classe responsável pelo acesso a dados (DAO) da entidade Livro.
 * Implementa as operações de CRUD utilizando JDBC puro e PreparedStatement
 * para garantir segurança contra SQL Injection.
 * * @author Gustavo Nunes
 * @version 1.0
 */


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

private static final String SQL_LIST_ALL = "SELECT * FROM T_LIVRO ORDER BY titulo";

    /**
     * Recupera todos os livros cadastrados no banco de dados.
     * * @return Uma lista de objetos Livro contendo os dados principais.
     * @throws RuntimeException em caso de erro de conexão ou SQL.
     */
    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_LIST_ALL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Livro livro = new Livro();
                livro.setId(rs.getLong("id"));
                livro.setTitulo(rs.getString("titulo"));
                livro.setEditora(rs.getString("editora"));
                livro.setAno(rs.getInt("ano"));
                livro.setGenero(rs.getString("genero"));
                livro.setIsbn(rs.getString("isbn"));
                // Nota: Por performance, não carregamos a lista de autores na listagem geral.
                // Isso seria feito num método 'buscarPorId' (Ver Detalhes).
                
                livros.add(livro);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar livros do catálogo", e);
        }
        return livros;
    }

    // Query com JOIN para buscar tanto no Título do Livro quanto no Nome do Autor
    private static final String SQL_BUSCA = 
        "SELECT DISTINCT l.* FROM T_LIVRO l " +
        "LEFT JOIN T_OBRA_AUTORES oa ON l.id = oa.id_livro " +
        "LEFT JOIN T_PESSOA p ON oa.id_pessoa = p.id " +
        "WHERE LOWER(l.titulo) LIKE LOWER(?) OR LOWER(p.nome_completo) LIKE LOWER(?) " +
        "ORDER BY l.titulo";

    /**
     * Busca livros por termo (título ou nome do autor).
     * Atende ao requisito de segurança contra SQL Injection.
     */
    public List<Livro> buscarPorTermo(String termo) {
        List<Livro> livros = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SQL_BUSCA)) {

            // O usuário digita "tolkien", nós transformamos em "%tolkien%"
            // Isso permite buscar partes do nome.
            String termoLike = "%" + termo + "%";
            
            stmt.setString(1, termoLike); // Substitui o primeiro ? (Titulo)
            stmt.setString(2, termoLike); // Substitui o segundo ? (Autor)
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Livro livro = new Livro();
                    livro.setId(rs.getLong("id"));
                    livro.setTitulo(rs.getString("titulo"));
                    livro.setEditora(rs.getString("editora"));
                    livro.setAno(rs.getInt("ano"));
                    livro.setGenero(rs.getString("genero"));
                    livro.setIsbn(rs.getString("isbn"));
                    livros.add(livro);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro na busca de livros", e);
        }
        return livros;
    }

}