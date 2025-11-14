package fiap.com.br.start_trek.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.entity.*;
import fiap.com.br.start_trek.repository.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    private final TrabalhoRepository trabalhoRepository;

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id){
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException( "Link não encontrado com este ID: " + id));
    }

    public List<Categoria> listarPorTrabalho(Long idTrabalho){
        return categoriaRepository.findAll().stream()
            .filter(l -> l.getTrabalho().getIdTrabalho().equals(idTrabalho))
            .collect(Collectors.toList());
    }

    public Categoria criarCategoria(Long idTrabalho, Categoria categoria){
        Trabalho trabalho = trabalhoRepository.findById(idTrabalho)
            .orElseThrow(() -> new RuntimeException("Trabalho não encontrado" + idTrabalho));
        validarTituloEConteudo(categoria);
        categoria.setTrabalho(trabalho);
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, Categoria novaCategoria){
        Categoria existente = buscarPorId(id);

        if(novaCategoria.getNomeCategoria() != null){
            existente.setNomeCategoria(novaCategoria.getNomeCategoria());
        }
        if(novaCategoria.getNomeCategoria() != null){
            existente.setConteudoCategoria(novaCategoria.getConteudoCategoria());
        }

        validarTituloEConteudo(existente);
        return categoriaRepository.save(existente);
    }

    public void excluir(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new RuntimeException("Não encontrei a categoria para a exclusão." + id);
        }
        categoriaRepository.deleteById(id);
    }

    private void validarTituloEConteudo(Categoria categoria){
        if(categoria.getNomeCategoria() == null || categoria.getNomeCategoria().trim().isEmpty()){
            throw new RuntimeException("O nome da categoria e obrigatorio.");
        }
        if(categoria.getConteudoCategoria() == null || categoria.getConteudoCategoria().toString().isEmpty()){
            throw new RuntimeException("Conteudo é obrigatorio.");
        }
    } 

}
