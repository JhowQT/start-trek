package fiap.com.br.start_trek.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TrabalhoService {
    
    private final TrabalhoRepository trabalhoRepository;
    private final CategoriaRepository categoriaRepository;

    public List<Trabalho> listarTodos(){
        return trabalhoRepository.findAll();
    }

    public List<Trabalho> listarPorCategoria(Long idCategoria){
        return trabalhoRepository.findByCategoriaIdCategoria(idCategoria);
    }

    public Trabalho buscarPorID(Long id){
        return trabalhoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Trabalho não encontrado com este ID: " + id));
    }

    public Trabalho cadastrar(Long idCategoria, Trabalho trabalho){
        
        Categoria categoria = categoriaRepository.findById(idCategoria)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + idCategoria));
        
        validarNomeELimite(trabalho.getNomeTrabalho());
        validarDuplicidadeDeTitulo(trabalho.getNomeTrabalho(), null);

        trabalho.setCategoria(categoria);

        return trabalhoRepository.save(trabalho);
    }

    public Trabalho atualizar(Long id, Trabalho novoTrabalho){
        Trabalho existente = buscarPorID(id);

        if(novoTrabalho.getNomeTrabalho() != null){
            validarNomeELimite(novoTrabalho.getNomeTrabalho());
            validarDuplicidadeDeTitulo(novoTrabalho.getNomeTrabalho(), id);
            existente.setNomeTrabalho(novoTrabalho.getNomeTrabalho());
        }

        if(novoTrabalho.getConteudoTrabalho() != null){
            existente.setConteudoTrabalho(novoTrabalho.getConteudoTrabalho());
        }

        if(novoTrabalho.getCategoria() != null){
            Categoria categoria = categoriaRepository.findById(novoTrabalho.getCategoria().getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria inválida."));
            existente.setCategoria(categoria);
        }

        return trabalhoRepository.save(existente);
    }

    public void excluir(Long id){
        if(!trabalhoRepository.existsById(id)){
            throw new RuntimeException("Id deste trabalho não existe: " + id);
        }
        trabalhoRepository.deleteById(id);
    }
    
    private void validarNomeELimite(String titulo){
        if(titulo == null || titulo.trim().isEmpty()){
            throw new RuntimeException("Nome para este campo é obrigatório");
        }
        if(titulo.length() > 150){
            throw new RuntimeException("Limite de 150 caracteres excedido");
        }
    }

    private void validarDuplicidadeDeTitulo(String titulo, Long idAtual){
        boolean duplicado = trabalhoRepository.findAll().stream()
            .anyMatch(t -> t.getNomeTrabalho() != null 
                && t.getNomeTrabalho().equalsIgnoreCase(titulo)
                && (idAtual == null || !t.getIdTrabalho().equals(idAtual)));
    
        if(duplicado){
            throw new RuntimeException("Este título já existe");
        }
    }
}
