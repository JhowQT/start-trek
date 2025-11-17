package fiap.com.br.start_trek.service;

import fiap.com.br.start_trek.dto.LoginCreateDTO;
import fiap.com.br.start_trek.dto.LoginResponseDTO;
import fiap.com.br.start_trek.entity.Usuario;
import fiap.com.br.start_trek.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UsuarioRepository usuarioRepository;

    public LoginResponseDTO autenticar(LoginCreateDTO loginDTO) {

        Usuario usuario = usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(loginDTO.getEmail()) &&
                        u.getSenha().trim().equals(loginDTO.getSenha().trim()))
                .findFirst()
                .orElse(null);

        if (usuario == null) {
            throw new RuntimeException("E-mail ou senha incorretos.");
        }

        String token = "fake-jwt-token-" + usuario.getIdUsuario();

        return new LoginResponseDTO(token, usuario);
    }
}
