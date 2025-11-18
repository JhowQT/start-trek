package fiap.com.br.start_trek.service;

import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.repository.*;
import fiap.com.br.start_trek.dto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final TrabalhoRepository trabalhoRepository;

    public List<ComentarioResponseDTO> listarTodos() {
        return comentarioRepository.findAll()
                .stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getIdComentario(),
                        c.getTrabalho().getIdTrabalho(),
                        c.getUsuario().getIdUsuario(),
                        c.getConteudoComentario(),
                        c.getUsuario().getNomeUsuario(),
                        c.getTrabalho().getNomeTrabalho()))
                .collect(Collectors.toList());
    }

    public List<ComentarioResponseDTO> listarPortrilha(Long idTrilha) {
        return comentarioRepository.findByTrabalhoIdTrabalho(idTrilha)
                .stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getIdComentario(),
                        c.getTrabalho().getIdTrabalho(),
                        c.getUsuario().getIdUsuario(),
                        c.getConteudoComentario(),
                        c.getUsuario().getNomeUsuario(),
                        c.getTrabalho().getNomeTrabalho()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ComentarioResponseDTO criarComentario(ComentarioCreateDTO dto, String emailUsuarioLogado) {

        Usuario usuario = usuarioRepository.findByEmail(emailUsuarioLogado)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Trabalho trabalho = trabalhoRepository.findById(dto.getIdTrabalho())
                .orElseThrow(() -> new RuntimeException("Trabalho não encontrado"));

        Comentario comentario = new Comentario();
        comentario.setConteudoComentario(dto.getConteudoComentario());
        comentario.setAtivoComentario("1");
        comentario.setUsuario(usuario);   // <-- vem do JWT
        comentario.setTrabalho(trabalho);

        Comentario salvo = comentarioRepository.save(comentario);

        return new ComentarioResponseDTO(
                salvo.getIdComentario(),
                trabalho.getIdTrabalho(),
                usuario.getIdUsuario(),
                salvo.getConteudoComentario(),
                usuario.getNomeUsuario(),
                trabalho.getNomeTrabalho()
        );
    }
}
