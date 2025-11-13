package fiap.com.br.start_trek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fiap.com.br.start_trek.entity.*;

@Repository
public interface Esp32Repository extends JpaRepository<Esp32, Long>{
    
}
