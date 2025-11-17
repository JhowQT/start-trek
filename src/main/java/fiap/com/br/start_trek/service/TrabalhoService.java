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

    public List<Trabalho> listarTodos(){
        return trabalhoRepository.findAll();
    }

    public Trabalho buscarPorID(Long id){
        return trabalhoRepository.findById(id)
            .orElseThrow(()-> new RuntimeException("Trabalho não encontrado com este ID: " + id));
    }

    public Trabalho cadastrar(Trabalho trabalho){
        validarNomeELimite(trabalho.getNomeTrabalho());
        validarDuplicidadeDeTitulo(trabalho.getNomeTrabalho(),null);
        return trabalhoRepository.save(trabalho);
    }

    public Trabalho atualizar(Long id, Trabalho novoTrabalho){
        Trabalho existente = buscarPorID(id);

        if(novoTrabalho.getNomeTrabalho() != null){
            validarNomeELimite(novoTrabalho.getNomeTrabalho());
            validarDuplicidadeDeTitulo(novoTrabalho.getNomeTrabalho(),id);
            existente.setNomeTrabalho(novoTrabalho.getNomeTrabalho());
        }
        if(novoTrabalho.getConteudoTrabalho() != null){
            existente.setConteudoTrabalho(novoTrabalho.getConteudoTrabalho());
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
            throw new RuntimeException("Nome para este campo obrigatorio");
        }
        if(titulo.length() > 150){
            throw new RuntimeException("Limite de 150 caracteres excedido para este campo");
        }
    }

    private void validarDuplicidadeDeTitulo(String titulo, Long idAtual){
        boolean duplicado = trabalhoRepository.findAll().stream()
            .anyMatch(t -> t.getNomeTrabalho() != null 
                && t.getNomeTrabalho().equalsIgnoreCase(titulo)
                && (idAtual == null || !t.getIdTrabalho().equals(idAtual)));
    
        if(duplicado){
            throw new RuntimeException("Este titulo existe, procure outro");
        }
    }


}
