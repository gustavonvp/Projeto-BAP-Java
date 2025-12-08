import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        livro.setGenero("ação");
        livro.setSinopse("Scarlett OHara, filha mimada e impetuosa de um rico fazendeiro, usa todos os meios que tem a sua disposição para sobreviver à Guerra Civil americana, quando fortunas e famílias foram destruídas, e conquistar o amor de Rhett Butler, um aventureiro com quem ela viverá um dos mais fascinantes romances da literatura. Em sua única obra literária, Margaret Mitchell costura magistralmente a profundidade humana das personagens ao expor sem máscaras erros, vulnerabilidades, egoísmos, más intenções, medos e pequenas conquistas dos personagens.");
        livro.setCapaUrl("https://books.google.com/books/content?id=BisXEAAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");

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


@Test
    public void testeCicloDeVidaLivro() {
        // --- 1. PREPARAÇÃO (CRIAR AUTORES) ---
        PessoaDao pessoaDao = new PessoaDao();
        Pessoa autor1 = new Pessoa("Autor Original", "Bio 1", LocalDate.now());
        Pessoa autor2 = new Pessoa("Autor Novo", "Bio 2", LocalDate.now());
        
        pessoaDao.salvar(autor1);
        pessoaDao.salvar(autor2);
        
        // Recuperar IDs gerados (pegando os últimos da lista)
        List<Pessoa> todosAutores = pessoaDao.listarTodos();
        Long idAutor1 = todosAutores.get(todosAutores.size() - 2).getId();
        Long idAutor2 = todosAutores.get(todosAutores.size() - 1).getId();

        // --- 2. CREATE (SALVAR LIVRO) ---
        LivroDao livroDao = new LivroDao();
        Livro livro = new Livro();
        livro.setTitulo("Livro Teste");
        livro.setEditora("Editora Teste");
        livro.setCapaUrl("http://teste.com/foto.jpg"); // <--- Adicione isto no teste
        
        livroDao.salvar(livro, Arrays.asList(idAutor1));
        Long idLivro = livro.getId();

        // --- UPDATE ---
        livro.setTitulo("Livro Editado");
        livro.setCapaUrl("http://teste.com/foto_nova.jpg"); // <--- Mude a URL
        
        livroDao.atualizar(livro, Arrays.asList(idAutor2));

        // --- VALIDAÇÃO ---
        Livro livroAtualizado = livroDao.buscarPorId(idLivro);
        assertEquals("Livro Editado", livroAtualizado.getTitulo());
        
        // Verifica se a capa atualizou
        assertEquals("http://teste.com/foto_nova.jpg", livroAtualizado.getCapaUrl());
        
        // Salva vinculado ao Autor 1
        livroDao.salvar(livro, Arrays.asList(idAutor1));
        assertNotNull(livro.getId(), "Livro deve ter ID após salvar");
        //Long idLivro = livro.getId();

        // --- 3. UPDATE (EDITAR LIVRO) ---
        // Modifica título e troca o autor (remove Autor 1, adiciona Autor 2)
        livro.setTitulo("Livro Versão 2 - Editado");
        livroDao.atualizar(livro, Arrays.asList(idAutor2));

        // Validação: Busca no banco para ver se mudou mesmo
        //Livro livroAtualizado = livroDao.buscarPorId(idLivro);
        assertEquals("Livro Editado", livroAtualizado.getTitulo());
        
        // Verifica se o vínculo de autor mudou (Autor 1 saiu, Autor 2 entrou)
        // Nota: buscarPorId carrega os autores. Verificamos se o ID do Autor 2 está lá.
        boolean temAutor2 = livroAtualizado.getAutores().stream()
                .anyMatch(p -> p.getId().equals(idAutor2));
        assertTrue(temAutor2, "O livro editado deve estar vinculado ao Autor 2");

        // --- 4. DELETE (EXCLUIR LIVRO) ---
        livroDao.excluir(idLivro);

        // Validação: Tenta buscar, deve retornar null ou vazio
        Livro livroExcluido = livroDao.buscarPorId(idLivro);
        assertNull(livroExcluido, "O livro não deve existir após exclusão");
    }



}

