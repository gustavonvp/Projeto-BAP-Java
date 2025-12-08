import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import br.com.projeto.bap.dao.LivroDao;
import br.com.projeto.bap.dao.PessoaDao;
import br.com.projeto.bap.model.Livro;
import br.com.projeto.bap.model.Pessoa;

public class LivroTest {

    @Test
    public void testeSalvarLivroComAutor() {
        // 1. Preparação: Precisamos de um Autor no banco antes
        Pessoa autor = new Pessoa("Autor Teste JUnit", "Bio Teste", LocalDate.now());
        PessoaDao pessoaDao = new PessoaDao();
        pessoaDao.salvar(autor);

        // Verifica se o autor ganhou um ID (foi salvo)
        // Precisamos buscar o ID dele. Como o método salvar(void) atual não retorna ID,
        // vamos buscar o último inserido ou simular que já sabemos.
        // NOTA: Para um teste real robusto, o método salvar() deveria atualizar o ID no objeto.
        // Vamos assumir que pegamos o autor da lista para garantir o ID:
        List<Pessoa> todos = pessoaDao.listarTodos();
        Pessoa autorSalvo = todos.get(todos.size() - 1); // Pega o último
        Long idAutor = autorSalvo.getId();

        // 2. Criar o Livro
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste BAP");
        livro.setEditora("Editora JUnit");
        livro.setAno(2025);
        livro.setIsbn("123-456");

        // 3. Ação: Salvar Livro vinculando ao Autor
        LivroDao livroDao = new LivroDao();
        
        // Passamos o livro e uma lista contendo o ID do autor
        assertDoesNotThrow(() -> {
            livroDao.salvar(livro, Arrays.asList(idAutor));
        });

        // 4. Validação: O livro deve ter ganhado um ID
        assertNotNull(livro.getId(), "O ID do livro não deveria ser nulo após salvar");
        System.out.println("Livro salvo com sucesso! ID Gerado: " + livro.getId());
    }
}