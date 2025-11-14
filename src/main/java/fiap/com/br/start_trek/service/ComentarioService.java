package fiap.com.br.start_trek.service;

import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.repository.*;
import fiap.com.br.start_trek.dto.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ComentarioService {
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private TrabalhoRepository trabalhoRepository;

    public List<ComentarioResponseDTO> listarTodos(){
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
 
    public List<ComentarioResponseDTO> listarPortrilha(Long idTrilha){
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
    public ComentarioResponseDTO criarComentario(ComentarioCreateDTO dto){
        Comentario comentario = new Comentario();
        
        comentario.setConteudoComentario(dto.getConteudoComentario());
        comentario.setAtivoComentario("1");

        comentario.setUsuario(usuarioRepository.findById(dto.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado")));
        
        comentario.setTrabalho(trabalhoRepository.findById(dto.getIdTrabalho())
            .orElseThrow(() -> new RuntimeException("Trabalho não encontrado")));
        
        Comentario salvo = comentarioRepository.save(comentario);
        
        return new ComentarioResponseDTO(
            salvo.getIdComentario(),
            salvo.getTrabalho().getIdTrabalho(),
            salvo.getUsuario().getIdUsuario(),
            salvo.getConteudoComentario(),
            salvo.getUsuario().getNomeUsuario(),
            salvo.getTrabalho().getNomeTrabalho()
        );
    }
}
