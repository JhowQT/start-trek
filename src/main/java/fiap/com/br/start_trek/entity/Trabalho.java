package fiap.com.br.start_trek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_ST_TRABALHO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Trabalho {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_trabalho")
    private Long idTrabalho;

    @Column(name = "nm_trabalho", nullable = false, length = 150)
    private String nomeTrabalho;

    @Lob
    @Column(name = "cd_trabalho")
    private String conteudoTrabalho;
}
