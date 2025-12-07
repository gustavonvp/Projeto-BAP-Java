package br.com.projeto.bap.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import br.com.projeto.bap.dao.PessoaDao;
import br.com.projeto.bap.model.Pessoa;
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
        pessoa.setNomeCompleto(nome);
        pessoa.setBiografia(biografia);
        pessoa.setDataNascimento(dataNascimento);

        // 4. Chamar o DAO para salvar no Banco (Persistência)
        PessoaDao dao = new PessoaDao();
        
        try {
            dao.salvar(pessoa);
            
            // 5. Redirecionar (Post-Redirect-Get)
            // Se der certo, voltamos para a Home (ou futuramente para a Lista)
            // Adicionamos um parâmetro ?sucesso=true para mostrar mensagem depois
            response.sendRedirect("index.jsp?msg=sucesso");
            
        } catch (Exception e) {
            e.printStackTrace();
            // Se der erro, redireciona com mensagem de erro
            response.sendRedirect("cadastro-pessoa.jsp?msg=erro");
        }
    }
}