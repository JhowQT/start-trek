package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class CategoriaProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_CATEGORIA no Oracle.
     *
     * @param nome Nome da categoria
     * @param conteudo Descrição da categoria (CLOB)
     */
    public void inserirCategoria(String nome, String conteudo) {

        String procedure = "{ call PR_INSERIR_CATEGORIA(?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, nome);
            stmt.setString(2, conteudo);

            stmt.execute();

            System.out.println("PR_INSERIR_CATEGORIA executada → Categoria: " + nome);

        } catch (SQLException e) {

            System.err.println("Erro ao executar PR_INSERIR_CATEGORIA: " + e.getMessage());
        }
    }
}
