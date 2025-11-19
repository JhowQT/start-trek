package fiap.com.br.start_trek.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import fiap.com.br.start_trek.dto.LoginCreateDTO;
import fiap.com.br.start_trek.dto.LoginResponseDTO;
import fiap.com.br.start_trek.entity.Usuario;
import fiap.com.br.start_trek.repository.UsuarioRepository;
import fiap.com.br.start_trek.security.TokenService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginCreateDTO loginDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.getEmail(),
                        loginDTO.getSenha()
                )
        );

        String token = tokenService.generateToken(authentication.getName());

        Usuario usuario = usuarioRepository
                .findByEmail(authentication.getName())
                .orElse(null);

        return new LoginResponseDTO(token, usuario);
    }
}
