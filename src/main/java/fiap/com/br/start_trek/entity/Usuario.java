package fiap.com.br.start_trek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_ST_USUARIO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nm_usuario", nullable = false, length = 100)
    private String nomeUsuario;

    @Column(name = "email", nullable = false, length = 150)
    private String email;

    @Column(name = "senha", nullable = false, length = 150)
    private String senha;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "at_usuario", length = 1)
    private String ativo;

    @ManyToOne
    @JoinColumn(name = "id_tipo_usuario", nullable = false)
    private TipoUsuario tipoUsuario;

    @ManyToOne
    @JoinColumn(name = "id_esp32", nullable = false)
    private Esp32 esp32;
    

}
