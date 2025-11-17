package fiap.com.br.start_trek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class StartTrekApplication {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		System.out.print("DIGITE O RM: ");
		String user = scanner.nextLine().trim();		// trim para tirar os espaços no inicio e final

		System.out.print("DIGITE A SENHA: ");
		String password = scanner.nextLine().trim();	// trim para tirar os espaçõs inicio e final

		System.setProperty("DB_USER", user);
		System.setProperty("DB_PASSWORD", password);
		
		System.out.println("\nCredencias Oracle definidas com sucesso!");
		System.out.println("Iniciando aplicação Orangue-Oracle...\n");

		SpringApplication.run(StartTrekApplication.class, args);

	}	
}
