package fiap.com.br.start_trek.executor;

import fiap.com.br.start_trek.StartTrekApplication;
import fiap.com.br.start_trek.oracledao.UsuarioProcedureDAO;
import fiap.com.br.start_trek.oracledao.TrabalhoProcedureDAO;
import fiap.com.br.start_trek.oracledao.ComentarioProcedureDAO;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

public class MainExecutor {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       EXECUÇÃO MANUAL DAS PROCEDURES         ");
        System.out.println("==============================================\n");

        Scanner scanner = new Scanner(System.in);

        // Coleta do usuário Oracle
        System.out.print("Digite seu usuário Oracle (ex: rm******): ");
        String user = scanner.nextLine().trim();

        System.out.print("Digite sua senha Oracle: ");
        String password = scanner.nextLine().trim();

        // Define credenciais como variáveis do sistema
        System.setProperty("DB_USER", user);
        System.setProperty("DB_PASSWORD", password);

        System.out.println("\nCredenciais Oracle definidas com sucesso!");
        System.out.println("Conectando e executando procedures...\n");

        // Inicializa o Spring sem servidor web
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(StartTrekApplication.class)
                        .web(WebApplicationType.NONE)
                        .run(args);

        // Pega os beans do DAO
        UsuarioProcedureDAO usuarioDAO = context.getBean(UsuarioProcedureDAO.class);
        TrabalhoProcedureDAO trabalhoDAO = context.getBean(TrabalhoProcedureDAO.class);
        ComentarioProcedureDAO comentarioDAO = context.getBean(ComentarioProcedureDAO.class);

        try {

            System.out.println("==============================================");
            System.out.println(" PROCEDURE: INSERIR USUÁRIO");
            System.out.println("==============================================");

            usuarioDAO.inserirUsuario(
                    "Capitão Kirk",
                    "kirk@startrek.com",
                    "senha123",
                    1,     
                    1     
            );

            System.out.println("\nProcedure de USUÁRIO executada com sucesso!\n");

            /*System.out.println("==============================================");
            System.out.println(" PROCEDURE: INSERIR TRABALHO");
            System.out.println("==============================================");

            trabalhoDAO.inserirTrabalho(
                    "Mapa Estelar",
                    "Conteúdo do trabalho inserido via DAO.",
                    1  
            );

            System.out.println("\nProcedure de TRABALHO executada com sucesso!\n");

            System.out.println("==============================================");
            System.out.println(" PROCEDURE: INSERIR COMENTÁRIO");
            System.out.println("==============================================");

            comentarioDAO.inserirComentario(
                    "Comentário enviado via DAO.",
                    "kirk@startrek.com",
                    1  
            );

            System.out.println("\nProcedure de COMENTÁRIO executada com sucesso!\n");*/

            System.out.println("==============================================");
            System.out.println(" TODAS AS PROCEDURES EXECUTADAS COM SUCESSO! ");
            System.out.println("==============================================");

        } catch (Exception e) {
            System.err.println("\nErro durante execução das procedures: " + e.getMessage());
        } finally {
            context.close();
        }

        System.out.println("\n==============================================");
        System.out.println("     EXECUÇÃO FINALIZADA COM SUCESSO");
        System.out.println("==============================================\n");
    }
}
