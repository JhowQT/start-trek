package fiap.com.br.start_trek.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class UsuarioCreateDTO {
    
    @NotBlank(message = "Nome obrigatorio")
    private String nomeUsuario;

    @Email(message = "O email não e valido")
    @NotBlank(message = "Email e obrigatorio")
    private String email;

    @NotBlank(message = "Senha obrigatoria")
    @Size(min = 8, max = 50, message = "A senha deve ter entre 8 a 50 caracteres")
    private String senha;

    @NotNull(message = "Tipo do usuario obrigatorio")
    private Long idTipoUsuario;

}
