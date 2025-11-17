package fiap.com.br.start_trek.service;

import java.util.List;

import org.springframework.stereotype.Service;

import org.springframework.data.domain.*;
import fiap.com.br.start_trek.entity.TipoUsuario;
import fiap.com.br.start_trek.repository.TipoUsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoUsuarioService {

    private final TipoUsuarioRepository tipoUsuarioRepository;

    public List<TipoUsuario> listarTodos(){
        return tipoUsuarioRepository.findAll();
    }

    /*public TipoUsuario buscarPorID(Long id){
        return tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Não encontramos o usuario com este ID: " + id));
    }*/

    // NOVO: Método com paginação (igual ao do professor)
    public Page<TipoUsuario> listarTodos(Pageable pageable){
        return tipoUsuarioRepository.findAll(pageable);
    }

    public TipoUsuario buscarPorID(Long id){
        return tipoUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Não encontramos o usuario com este ID: " + id));
    }

    
}
