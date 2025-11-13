package fiap.com.br.start_trek.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ComentarioResponseDTO {
    
    private Long idComentario;
    private Long idTrabalho;
    private Long idUsuario;

    private String conteudoComentario;
    private String nomeUsuario;
    private String nomeTrabalho;
}
