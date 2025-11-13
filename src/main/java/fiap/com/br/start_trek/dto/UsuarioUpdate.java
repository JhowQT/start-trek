package fiap.com.br.start_trek.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioUpdate {
    private String nomeUsuario;
    private String email;
    private String senha;
    private Long idTipoUsuario;
}
