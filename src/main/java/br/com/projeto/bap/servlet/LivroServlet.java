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

/**
 * Controlador (Servlet) responsável pelo fluxo de cadastro e listagem de Livros.
 * Recebe as requisições HTTP da View (JSP), interage com o Model (DAO)
 * e devolve a resposta adequada.
 */

@WebServlet("/livro")
public class LivroServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Gerencia as requisições GET.
     * Se o parâmetro 'acao' for 'listar', exibe a tabela de livros.
     * Caso contrário, prepara e exibe o formulário de cadastro.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        LivroDao livroDao = new LivroDao();

        if ("listar".equals(acao)) {
            // 1. Busca a lista de livros no banco
            List<Livro> listaLivros = livroDao.listarTodos();
            
            // 2. Disponibiliza para o JSP
            request.setAttribute("listaLivros", listaLivros);
            
            // 3. Encaminha para a tela de listagem
            RequestDispatcher dispatcher = request.getRequestDispatcher("lista-livros.jsp");
            dispatcher.forward(request, response);
        }else if ("buscar".equals(acao)) {
        // --- NOVA LÓGICA DE BUSCA ---
        String termo = request.getParameter("termo");
        List<Livro> listaFiltrada;
        
        if (termo != null && !termo.trim().isEmpty()) {
            listaFiltrada = livroDao.buscarPorTermo(termo);
        } else {
            // Se a busca estiver vazia, traz tudo
            listaFiltrada = livroDao.listarTodos();
        }
        
        // Reutilizamos a MESMA página de lista para mostrar o resultado
        request.setAttribute("listaLivros", listaFiltrada);
        RequestDispatcher dispatcher = request.getRequestDispatcher("lista-livros.jsp");
        dispatcher.forward(request, response);
        } else {
            // FLUXO DE CADASTRO (Padrão)
            // Carrega os autores para preencher o <select> do formulário
            PessoaDao pessoaDao = new PessoaDao();
            List<Pessoa> listaAutores = pessoaDao.listarTodos();
            
            request.setAttribute("listaAutores", listaAutores);
            
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