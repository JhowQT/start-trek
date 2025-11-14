package fiap.com.br.start_trek.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.dto.*;
import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.repository.*;
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

    public UsuarioResponseDTO buscarUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado com o ID: " + id));
        return toResponseDTO(usuario);
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioUpdateDTO dto){
       
        Usuario existente = usuarioRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Usuario não encontrado" + id));

        if(dto.getNomeUsuario() != null && !dto.getNomeUsuario().isBlank())
            existente.setNomeUsuario(dto.getNomeUsuario());
        if(dto.getEmail() != null && !dto.getEmail().isBlank())
            existente.setEmail(dto.getEmail());
        if(dto.getSenha() != null && !dto.getSenha().isBlank()){
            if(dto.getSenha().length() < 8)
                throw new RuntimeException("A senha deve ter 8 caracteres no minimo.");
            existente.setSenha(dto.getSenha());
        }
        if(dto.getIdTipoUsuario() != null){
            TipoUsuario tipo = tipoUsuarioRepository.findById(dto.getIdTipoUsuario())
                .orElseThrow(() -> new RuntimeException("Tipo de usuario invalido."));
            existente.setTipoUsuario(tipo);
        }
        Usuario atualizado = usuarioRepository.save(existente);
        return toResponseDTO(atualizado);
    }

    public UsuarioResponseDTO atualizarFoto(Long id, UsuarioFotoDTO dto) throws java.io.IOException{
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario não encontrado com o ID: " + id));

        try {
            usuario.setFoto(dto.getFoto().getBytes());
        }catch(IOException e){
            throw new RuntimeException("Erro ao processar a foto do usuario.");
        }
        Usuario atualizado = usuarioRepository.save(usuario);
        return toResponseDTO(atualizado);
    }

    public void deletarUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new RuntimeException("Usuario não encontrado com o ID: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario){
        String fotoBase64 = null;

        if (usuario.getFoto() != null &&  usuario.getFoto().length > 0){
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
