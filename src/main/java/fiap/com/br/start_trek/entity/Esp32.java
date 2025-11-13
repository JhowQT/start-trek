package fiap.com.br.start_trek.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "T_ST_ESP32")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Esp32 {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_esp32")
    private Long idEsp32;

}
