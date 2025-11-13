package fiap.com.br.start_trek.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data
public class UsuarioFotoDTO {
    private MultipartFile foto;
}
