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
     * OBS: idEsp32 é temporariamente fixado com valor 1,
     * pois a tabela ESP32 está em desenvolvimento.
     */
    public void inserirUsuario(String nome, String email, String senha, int idTipo, int idEsp32Ignorado) {

        int idEsp32 = 1;

        String procedure = "{ call PR_INSERIR_USUARIO(?, ?, ?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, senha);
            stmt.setInt(4, idTipo);
            stmt.setInt(5, idEsp32); // sempre envia 1

            stmt.execute();

            System.out.println("✔ PR_INSERIR_USUARIO executada → Usuário: " + nome + " | ESP32 = " + idEsp32);

        } catch (SQLException e) {

            System.err.println("Erro ao executar PR_INSERIR_USUARIO: " + e.getMessage());

            int errorCode = e.getErrorCode();

            if (errorCode == 20001) {
                System.err.println("Email já cadastrado.");
            } else if (errorCode == 20002) {
                System.err.println("Tipo de usuário inexistente.");
            } else if (errorCode == 20003) {
                System.err.println("ESP32 inexistente.");
            }
        }
    }
}
