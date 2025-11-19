package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class UsuarioProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_USUARIO do Oracle.
     *
     * @param nome Nome do usuário
     * @param email Email
     * @param senha Senha
     * @param idTipo ID do tipo de usuário
     * @param idEsp32 ID do dispositivo ESP32
     */
    public void inserirUsuario(String nome, String email, String senha, int idTipo, int idEsp32) {

        String procedure = "{ call PR_INSERIR_USUARIO(?, ?, ?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setInt(4, idTipo);
            stmt.setInt(5, idEsp32);

            stmt.execute();

            System.out.println("✔ Procedure PR_INSERIR_USUARIO executada → Usuário: " + nome);

        } catch (SQLException e) {

            System.err.println("❌ Erro ao executar PR_INSERIR_USUARIO: " + e.getMessage());

            // Tratamento de erros gerados pelo RAISE_APPLICATION_ERROR
            if (e.getMessage().contains("20001")) {
                System.err.println("✖ Email já cadastrado.");
            } 
            else if (e.getMessage().contains("20002")) {
                System.err.println("✖ Tipo de usuário inexistente.");
            } 
            else if (e.getMessage().contains("20003")) {
                System.err.println("✖ ESP32 inexistente.");
            }
        }
    }
}
