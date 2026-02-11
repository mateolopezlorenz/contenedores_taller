package daw2026.taller_contenedores.repository;

import daw2026.taller_contenedores.model.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {
}

