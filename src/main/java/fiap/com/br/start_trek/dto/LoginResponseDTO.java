package fiap.com.br.start_trek.dto;

import fiap.com.br.start_trek.entity.*;
import lombok.*;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private Usuario usuario;
}
