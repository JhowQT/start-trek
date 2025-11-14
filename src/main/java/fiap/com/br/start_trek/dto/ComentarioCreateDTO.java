package fiap.com.br.start_trek.dto;

import lombok.*;

@Getter
@Setter
public class ComentarioCreateDTO {
    
    private String conteudoComentario;
    private String ativoComentario = "1";
    private Long idUsuario;
    private Long idTrabalho;

}
