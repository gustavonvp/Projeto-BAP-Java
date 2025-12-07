package br.com.projeto.bap.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.projeto.bap.dao.LivroDao;
import br.com.projeto.bap.dao.PessoaDao;
import br.com.projeto.bap.model.Livro;
import br.com.projeto.bap.model.Pessoa;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // GET: Prepara o formulário (Carrega os autores) OU lista os livros
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");

        if ("listar".equals(acao)) {
            // Lógica futura para listar livros...
            // Por enquanto, redireciona para a home
            response.sendRedirect("index.jsp");
        } else {
            // AÇÃO PADRÃO: Abrir formulário de cadastro
            // 1. Busca todos os autores no banco para preencher o <select>
            PessoaDao pessoaDao = new PessoaDao();
            List<Pessoa> listaAutores = pessoaDao.listarTodos();
            
            // 2. Envia a lista para o JSP
            request.setAttribute("listaAutores", listaAutores);
            
            // 3. Abre a página de cadastro
            RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro-livro.jsp");
            dispatcher.forward(request, response);
        }
    }

    // POST: Recebe os dados do formulário para Salvar
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Receber dados simples
        String titulo = request.getParameter("titulo");
        String editora = request.getParameter("editora");
        String isbn = request.getParameter("isbn");
        String genero = request.getParameter("genero");
        String sinopse = request.getParameter("sinopse");
        
        // Converter Ano (tratar erro se vazio)
        Integer ano = null;
        try {
            String anoStr = request.getParameter("ano");
            if (anoStr != null && !anoStr.isEmpty()) {
                ano = Integer.parseInt(anoStr);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ano inválido");
        }

        // 2. Receber a lista de IDs dos Autores (Multi-Select)
        // O método getParameterValues retorna um Array de Strings
        String[] autoresIdsStr = request.getParameterValues("autoresIds");
        List<Long> idsAutores = new ArrayList<>();
        
        if (autoresIdsStr != null) {
            for (String idStr : autoresIdsStr) {
                try {
                    idsAutores.add(Long.parseLong(idStr));
                } catch (NumberFormatException e) {
                    // Ignora ID inválido
                }
            }
        }

        // 3. Criar o objeto Livro
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setEditora(editora);
        livro.setIsbn(isbn);
        livro.setAno(ano);
        livro.setGenero(genero);
        livro.setSinopse(sinopse);

        // 4. Chamar o DAO para salvar (Livro + Relacionamentos)
        LivroDao dao = new LivroDao();
        try {
            dao.salvar(livro, idsAutores);
            // Sucesso: volta para home com mensagem
            response.sendRedirect("index.jsp?msg=livroSalvo");
        } catch (Exception e) {
            e.printStackTrace();
            // Erro: volta para o formulário (idealmente manteria os dados preenchidos)
            response.sendRedirect("livro?msg=erro");
        }
    }
}