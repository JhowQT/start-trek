package fiap.com.br.start_trek.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    
    private Long idUsuario;
    private String nomeUsuario;
    private String email;

    private Long idTipoUsuario;
    private String nomeTipoUsuario;

    private String ativo;
    private String fotoBase64;

}
