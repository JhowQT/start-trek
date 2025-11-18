package fiap.com.br.start_trek.auth;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.entity.Usuario;
import fiap.com.br.start_trek.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {  // Ponte entro o Spring security e o banco de dados
    // busca o user que esta no BD
    // devolve no formato UserDetails do Spring Security
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return User.builder()
                .username(usuario.getEmail())           // O login será o email
                .password(usuario.getSenha())           // Senha já deve estar criptografada (BCrypt)
                .authorities("USER")                    // Regra padrão
                .build();
    }
}
