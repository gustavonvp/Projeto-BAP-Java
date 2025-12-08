

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import br.com.projeto.bap.dao.PessoaDao;
import br.com.projeto.bap.model.Pessoa;
import br.com.projeto.bap.util.ConnectionFactory;

public class PessoaTest {

    @Test
    public void testeConexao() {
        // Testa se consegue conectar ao Docker
        assertDoesNotThrow(() -> {
            try (Connection conn = ConnectionFactory.getConnection()) {
                assertNotNull(conn);
            }
        });
    }

    @Test
    public void testeSalvarPessoa() {
        // Cria um cenário de teste [cite: 327]
        Pessoa p = new Pessoa("Margareth Mitchellt", "Margaret nasceu em Atlanta, em 1900. Era filha do advogado Eugene Muse Mitchell, diretor da Sociedade de História de Atlanta, e de Mary Isabelle \"Maybelle\" Stephens, uma sufragista. Margaret cresceu ouvindo histórias sobre a Guerra de Secessão contadas por seus parentes e por veteranos confederados. Obcecada por escrever, conta-se que ainda em criança costumava levantar-se no meio da noite para registrar ideias para histórias e peças teatrais. Todavia, Margaret nunca foi exatamente uma aluna brilhante.\r\n" + //
                        "\r\n" + //
                        "No outono de 1918, ingressou no Smith College, de Northampton, Massachusetts, pouco antes da entrada dos Estados Unidos na Primeira Guerra Mundial. Durante o conflito, seu noivo, o tenente Clifford Henry, foi morto em combate na França e em janeiro de 1919, Maybelle Stephens faleceu durante a epidemia de Gripe Espanhola. Este último acontecimento obrigou Margaret a abandonar os estudos e voltar para casa, mas ela não possuía temperamento para dedicar-se apenas a cuidar do pai e do irmão mais velho. Logo, seu comportamento de melindrosa e seu trabalho em projetos sociais junto a população negra de Atlanta, começaram a escandalizar a sociedade conservadora da cidade.", LocalDate.of(1900, 11, 8));
        PessoaDao dao = new PessoaDao();

        // Executa a ação e verifica se não dá erro
        assertDoesNotThrow(() -> dao.salvar(p));
    }


    @Test
    public void testeEditarPessoa() {
        PessoaDao dao = new PessoaDao();
        Pessoa pessoa = dao.buscarPorId(1L); // 2. Busca a pessoa
        pessoa.setNomeCompleto("Nome Corrigido"); // 3. Muda o dado na memória

        // 1. Criar Pessoa com erro de digitação proposital
        Pessoa p = new Pessoa("George R.R. Martinho", "Escritor de GoT", LocalDate.of(1948, 9, 20));
        dao.salvar(p);

        // Recuperar ID
        List<Pessoa> lista = dao.listarTodos();
        Pessoa salva = lista.get(lista.size() - 1);
        Long id = salva.getId();

        // 2. Editar (Corrigir nome)
        salva.setNomeCompleto("George R.R. Martin"); // Nome correto
        salva.setBiografia("Autor das Crônicas de Gelo e Fogo");
        dao.atualizar(salva);

        // 3. Validar mudança
        Pessoa atualizada = dao.buscarPorId(id);
        assertEquals("George R.R. Martin", atualizada.getNomeCompleto());
        assertEquals("Autor das Crônicas de Gelo e Fogo", atualizada.getBiografia());
    }
}