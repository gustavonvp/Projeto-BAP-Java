package br.com.projeto.bap.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import br.com.projeto.bap.dao.PessoaDao;
import br.com.projeto.bap.model.Pessoa;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Define a URL que aciona este Servlet (mesmo nome do action do form)
@WebServlet("/pessoa")
public class PessoaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Método POST: Chamado quando o usuário clica em "Salvar" no formulário
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Receber ID (Se vier preenchido, é Edição)
        String idStr = request.getParameter("id");
        Long id = null;
        if (idStr != null && !idStr.isEmpty()) {
            try { id = Long.parseLong(idStr); } catch (NumberFormatException e) {System.out.println("ID inválido no POST: " + idStr);}
        }

        // 1. Receber os dados do HTML (Vêm todos como String)
        String nome = request.getParameter("nomeCompleto");
        String biografia = request.getParameter("biografia");
        String dataNascimentoStr = request.getParameter("dataNascimento");

        // 2. Converter os dados (String -> LocalDate)
        LocalDate dataNascimento = null;
        if (dataNascimentoStr != null && !dataNascimentoStr.isEmpty()) {
            try {
                dataNascimento = LocalDate.parse(dataNascimentoStr);
            } catch (DateTimeParseException e) {
                // Se a data for inválida, poderíamos retornar um erro, 
                // mas por simplicidade vamos deixar null
                e.printStackTrace();
            }
        }

        // 3. Montar o Objeto (Model)
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        pessoa.setNomeCompleto(nome);
        pessoa.setBiografia(biografia);
        pessoa.setDataNascimento(dataNascimento);

        // 4. Chamar o DAO para salvar no Banco (Persistência) // 4. Decidir: Salvar (Novo) ou Atualizar (Existente)
        PessoaDao dao = new PessoaDao(); 
        if (pessoa.getId() == null) {
            dao.salvar(pessoa);
        } else {
            dao.atualizar(pessoa); // Requer método atualizar no DAO
        }
        // 5. Redirecionar para a lista
     // ✅ SUCESSO: Redireciona para a lista
            // IMPORTANTE: Este é o ÚNICO ponto de redirecionamento de sucesso
            response.sendRedirect("pessoa?msg=salvo");
        
        try {
            dao.salvar(pessoa);
            
            // 5. Redirecionar (Post-Redirect-Get)
            // Se der certo, voltamos para a Home (ou futuramente para a Lista)
            // Adicionamos um parâmetro ?sucesso=true para mostrar mensagem depois
            response.sendRedirect("index.jsp?msg=sucesso");
            
        } catch (Exception e) {
            e.printStackTrace();
            // Se der erro, redireciona com mensagem de erro
            // erro redirecionamento duplo response.sendRedirect("cadastro-pessoa.jsp?msg=erro");
           // return;
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String acao = request.getParameter("acao");
        String idStr = request.getParameter("id");
    
        // --- DEBUG NO CONSOLE DO TOMCAT ---
        System.out.println(">>> PessoaServlet doGet invocado!");
        System.out.println(">>> Ação recebida: " + acao);
        System.out.println(">>> ID recebido: " + idStr);
        // ----------------------------------

        PessoaDao dao = new PessoaDao();
        
        if ("editar".equals(acao)) {
            // --- LÓGICA DE PREPARAR EDIÇÃO ---
            try {
                // Usa a variável idStr que capturamos acima
                if (idStr != null && !idStr.isEmpty()) {
                    Long id = Long.parseLong(idStr); // Converte "5" para 5
                    
                    Pessoa pessoa = dao.buscarPorId(id);
                    request.setAttribute("pessoa", pessoa);
                    
                    RequestDispatcher dispatcher = request.getRequestDispatcher("cadastro-pessoa.jsp");
                    dispatcher.forward(request, response);
                } else {
                    response.sendRedirect("pessoa"); // Se ID for vazio, volta pra lista
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("pessoa?erro=idInvalido");
            }

        } else if ("excluir".equals(acao)) {
            // LÓGICA DE EXCLUIR
            try {
                // Usa a variável idStr novamente
                if (idStr != null && !idStr.isEmpty()) {
                    Long id = Long.parseLong(idStr);
                    dao.excluir(id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Redireciona para limpar a URL
            response.sendRedirect("pessoa");

        } else {
            // LISTAGEM PADRÃO (Se não tiver ação ou ação for desconhecida)
            List<Pessoa> lista = dao.listarTodos();
            request.setAttribute("listaPessoas", lista);
            RequestDispatcher dispatcher = request.getRequestDispatcher("lista-pessoas.jsp");
            dispatcher.forward(request, response);
        }
    }
}