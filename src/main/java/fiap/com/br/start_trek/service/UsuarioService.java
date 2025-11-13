package fiap.com.br.start_trek.service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.dto.UsuarioCreateDTO;
import fiap.com.br.start_trek.dto.UsuarioResponseDTO;
import fiap.com.br.start_trek.entity.TipoUsuario;
import fiap.com.br.start_trek.entity.Usuario;
import fiap.com.br.start_trek.repository.TipoUsuarioRepository;
import fiap.com.br.start_trek.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final TipoUsuarioRepository tipoUsuarioRepository;

    public UsuarioResponseDTO cadastraUsuario(UsuarioCreateDTO dto){

        if(usuarioRepository.exexistsByEmail(dto.getEmail())){
            throw new RuntimeException("Já existe um Usuario com este email cadastro");
        }

        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(dto.getIdTipoUsuario())
            .orElseThrow(() -> new RuntimeException("Tipo de usuario invalido."));
    
        if(dto.getSenha() == null || dto.getSenha().length() < 8){
            throw new RuntimeException("A senha deve ter pelo menos 8 caracteres.");
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNomeUsuario(dto.getNomeUsuario());
        novoUsuario.setEmail(dto.getEmail());
        novoUsuario.setSenha(dto.getSenha());
        novoUsuario.setTipoUsuario(tipoUsuario);
        novoUsuario.setAtivo("1");

        Usuario salvo = usuarioRepository.save(novoUsuario);
        return toResponseDTO(salvo);
    }

    public List<UsuarioResponseDTO> listarUsuarios(){
        return usuarioRepository.findAll()
            .stream()
            .map(this::toResponseDTO)
            .collect(Collectors.toList());
    }




    private UsuarioResponseDTO toResponseDTO(Usuario usuario){
        String fotoBase64 = null;

        if(usuario.getFoto() != null &&  usuario.getFoto().length > 0){
            fotoBase64 = Base64.getEncoder().encodeToString(usuario.getFoto());
        }
        
        return new UsuarioResponseDTO(
            usuario.getIdUsuario(),
            usuario.getNomeUsuario(),
            usuario.getEmail(),
            usuario.getTipoUsuario().getIdTipoUsuario(),

            usuario.getTipoUsuario().getNomeTipoUsuario(),
            usuario.getAtivo(),
            fotoBase64
        );
    }

}
