package fiap.com.br.start_trek.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdateDTO {
    private String nomeUsuario;
    private String email;
    private String senha;
    private Long idTipoUsuario;
}
