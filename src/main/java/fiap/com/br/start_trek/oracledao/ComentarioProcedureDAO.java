package fiap.com.br.start_trek.oracledao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ComentarioProcedureDAO {

    @Autowired
    private DataSource dataSource;

    /**
     * Chama a procedure PR_INSERIR_COMENTARIO no Oracle.
     *
     * @param conteudo Conteúdo do comentário (CLOB)
     * @param email Email do usuário que comenta
     * @param idTrabalho ID do trabalho relacionado
     */
    public void inserirComentario(String conteudo, String email, int idTrabalho) {

        String procedure = "{ call PR_INSERIR_COMENTARIO(?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement stmt = conn.prepareCall(procedure)) {

            stmt.setString(1, conteudo);
            stmt.setString(2, email);
            stmt.setInt(3, idTrabalho);

            stmt.execute();

            System.out.println("PR_INSERIR_COMENTARIO executada → Usuário: " + email);

        } catch (SQLException e) {

            System.err.println("Erro ao executar PR_INSERIR_COMENTARIO: " + e.getMessage());

            int errorCode = e.getErrorCode();

            if (errorCode == 20020) {
                System.err.println("Email informado não está cadastrado.");
            }
            else if (errorCode == 20021) {
                System.err.println("O trabalho informado não existe.");
            }
        }
    }
}
