package fiap.com.br.start_trek.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fiap.com.br.start_trek.entity.Categoria;
import fiap.com.br.start_trek.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listarTodos(){
        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id){
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
    }

    public Categoria criarCategoria(Categoria categoria){
        validarTituloEConteudo(categoria);
        return categoriaRepository.save(categoria);
    }

    public Categoria atualizarCategoria(Long id, Categoria novaCategoria){
        Categoria existente = buscarPorId(id);

        if(novaCategoria.getNomeCategoria() != null){
            existente.setNomeCategoria(novaCategoria.getNomeCategoria());
        }
        if(novaCategoria.getConteudoCategoria() != null){
            existente.setConteudoCategoria(novaCategoria.getConteudoCategoria());
        }

        validarTituloEConteudo(existente);
        return categoriaRepository.save(existente);
    }

    public void excluir(Long id){
        if(!categoriaRepository.existsById(id)){
            throw new RuntimeException("Categoria não encontrada para exclusão: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    private void validarTituloEConteudo(Categoria categoria){
        if(categoria.getNomeCategoria() == null || categoria.getNomeCategoria().trim().isEmpty()){
            throw new RuntimeException("O nome da categoria é obrigatório.");
        }
        if(categoria.getConteudoCategoria() == null || categoria.getConteudoCategoria().trim().isEmpty()){
            throw new RuntimeException("O conteúdo é obrigatório.");
        }
    }
}
