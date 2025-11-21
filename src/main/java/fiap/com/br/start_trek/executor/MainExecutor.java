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
            System.out.println("  PROCEDURE: INSERIR 6 USUÁRIOS");
            System.out.println("==============================================");

            // 1
            usuarioDAO.inserirUsuario(
                    "Capitão Kirk",
                    "kirk@startrek.com",
                    "senha123",
                    2,     
                    1      
            );

            // 2
            usuarioDAO.inserirUsuario(
                    "Spock",
                    "spock@startrek.com",
                    "senha123",
                    2,
                    2
            );

            // 3
            usuarioDAO.inserirUsuario(
                    "Dr. McCoy",
                    "mccoy@startrek.com",
                    "senha123",
                    2,
                    3
            );

            // 4
            usuarioDAO.inserirUsuario(
                    "Uhura",
                    "uhura@startrek.com",
                    "senha123",
                    2,
                    4
            );

            // 5
            usuarioDAO.inserirUsuario(
                    "Scotty",
                    "scotty@startrek.com",
                    "senha123",
                    2,
                    5
            );

            // 6
            usuarioDAO.inserirUsuario(
                    "Sulu",
                    "sulu@startrek.com",
                    "senha123",
                    2,
                    6
            );

            System.out.println("\n6 usuários inseridos com sucesso!\n");

            System.out.println("==============================================");
            System.out.println("  PROCEDURE: INSERIR 3 TRABALHOS");
            System.out.println("==============================================");

            // Trabalho 1 -> categoria 1
            trabalhoDAO.inserirTrabalho(
                    "Trens Históricos",
                    "Trabalho sobre a história e evolução dos trens antigos.",
                    1  
            );

            // Trabalho 2 -> categoria 2
            trabalhoDAO.inserirTrabalho(
                    "Trens Modernos",
                    "Trabalho sobre trens de alta velocidade e tecnologias atuais.",
                    2  
            );

            // Trabalho 3 -> categoria 3
            trabalhoDAO.inserirTrabalho(
                    "Trens do Futuro",
                    "Trabalho sobre possíveis tecnologias e conceitos de trens futuristas.",
                    3   
            );

            System.out.println("\n3 trabalhos inseridos com sucesso!\n");

            System.out.println("==============================================");
            System.out.println("  PROCEDURE: INSERIR 6 COMENTÁRIOS");
            System.out.println("==============================================");

            // Aqui vou criar 6 comentários, um para cada usuário.
            // ATENÇÃO: estou assumindo que os trabalhos criados acima têm IDs 1, 2 e 3.
            // Se no seu banco os IDs forem diferentes, ajuste o último parâmetro (id_trabalho).

            // Usuário 1 (Kirk) comenta no Trabalho 1
            comentarioDAO.inserirComentario(
                    "Excelente análise histórica dos trens!",
                    "kirk@startrek.com",
                    1   // id_trabalho
            );

            // Usuário 2 (Spock) comenta no Trabalho 1
            comentarioDAO.inserirComentario(
                    "Lógico e bem estruturado.",
                    "spock@startrek.com",
                    1
            );

            // Usuário 3 (McCoy) comenta no Trabalho 2
            comentarioDAO.inserirComentario(
                    "Muito bom ver o impacto na área da saúde e logística.",
                    "mccoy@startrek.com",
                    2
            );

            // Usuário 4 (Uhura) comenta no Trabalho 2
            comentarioDAO.inserirComentario(
                    "Comunicação e tecnologia nos trens modernos foi bem explicada.",
                    "uhura@startrek.com",
                    2
            );

            // Usuário 5 (Scotty) comenta no Trabalho 3
            comentarioDAO.inserirComentario(
                    "As ideias de energia e propulsão são fascinantes.",
                    "scotty@startrek.com",
                    3
            );

            // Usuário 6 (Sulu) comenta no Trabalho 3
            comentarioDAO.inserirComentario(
                    "Ótimas projeções sobre mobilidade do futuro.",
                    "sulu@startrek.com",
                    3
            );

            System.out.println("\n6 comentários inseridos com sucesso!\n");

            System.out.println("==============================================");
            System.out.println("  TODAS AS PROCEDURES EXECUTADAS COM SUCESSO! ");
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
