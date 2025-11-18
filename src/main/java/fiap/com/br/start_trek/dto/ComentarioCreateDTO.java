package fiap.com.br.start_trek.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComentarioCreateDTO {

    private String conteudoComentario;
    private Long idTrabalho;
}
