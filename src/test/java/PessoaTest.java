

import java.sql.Connection;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        Pessoa p = new Pessoa("Autor Teste JUnit", "Bio gerada pelo teste", LocalDate.of(1990, 1, 1));
        PessoaDao dao = new PessoaDao();

        // Executa a ação e verifica se não dá erro
        assertDoesNotThrow(() -> dao.salvar(p));
    }
}