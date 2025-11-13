package fiap.com.br.start_trek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.com.br.start_trek.entity.*;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean exexistsByEmail(String email);
}
