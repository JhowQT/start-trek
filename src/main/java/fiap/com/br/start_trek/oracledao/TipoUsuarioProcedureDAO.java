package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class TipoUsuarioProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_TIPO_USUARIO no Oracle.
     *
     * @param nome Nome do tipo de usuário (ADMIN, USER, etc)
     */
    public void inserirTipoUsuario(String nome) {

        String procedure = "{ call PR_INSERIR_TIPO_USUARIO(?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, nome);
            stmt.execute();

            System.out.println("PR_INSERIR_TIPO_USUARIO executada → Tipo: " + nome);

        } catch (SQLException e) {
            System.err.println("Erro ao executar PR_INSERIR_TIPO_USUARIO: " + e.getMessage());
        }
    }
}
