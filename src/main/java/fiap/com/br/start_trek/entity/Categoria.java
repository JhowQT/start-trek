package fiap.com.br.start_trek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_ST_CATEGORIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idCategoria;

    @Column(name = "nm_categoria", nullable = false)
    private String nomeCategoria;

    @Lob
    @Column(name = "cd_categoria", nullable = false)
    private String conteudoCategoria;
}
