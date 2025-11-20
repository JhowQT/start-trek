package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class Esp32ProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_ESP32 no Oracle.
     * Esta procedure insere automaticamente um novo ESP32 usando o IDENTITY.
     */
    public void inserirEsp32() {

        String procedure = "{ call PR_INSERIR_ESP32() }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.execute();

            System.out.println("PR_INSERIR_ESP32 executada → Novo ESP32 criado.");

        } catch (SQLException e) {
            System.err.println("Erro ao executar PR_INSERIR_ESP32: " + e.getMessage());
        }
    }
}
