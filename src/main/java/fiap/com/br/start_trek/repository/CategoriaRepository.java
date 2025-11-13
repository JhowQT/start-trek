package fiap.com.br.start_trek.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.com.br.start_trek.entity.*;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    List<Categoria> findByTrabalhoIdTrabalho(Long idTrabalho);
}
