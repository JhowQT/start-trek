package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TrabalhoProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_TRABALHO no Oracle.
     *
     * @param nome Nome do trabalho
     * @param conteudo Conteúdo do trabalho (CLOB)
     * @param idCategoria ID da categoria (FK)
     */
    public void inserirTrabalho(String nome, String conteudo, int idCategoria) {

        String procedure = "{ call PR_INSERIR_TRABALHO(?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, nome);
            stmt.setString(2, conteudo);  // CLOB tratado como String
            stmt.setInt(3, idCategoria);

            stmt.execute();

            System.out.println("PR_INSERIR_TRABALHO executada → Trabalho: " + nome);

        } catch (SQLException e) {

            System.err.println("Erro ao executar PR_INSERIR_TRABALHO: " + e.getMessage());

            int errorCode = e.getErrorCode();

           
            if (errorCode == 20010) {
                System.err.println("Categoria inexistente.");
            }
            else if (errorCode == 20011) {
                System.err.println("Já existe um trabalho com esse nome.");
            }
        }
    }
}
