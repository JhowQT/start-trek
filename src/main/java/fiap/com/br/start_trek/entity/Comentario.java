package fiap.com.br.start_trek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_OR_COMENTARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Long idComentario;

    @Lob
    @Column(name = "cd_comentario", nullable = false)
    private String conteudoComentario;
    
    @Column(name = "at_comentario", length = 1)
    private String ativoComentario; 

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_trabalho", nullable = false)
    private Trabalho trabalho;

}
