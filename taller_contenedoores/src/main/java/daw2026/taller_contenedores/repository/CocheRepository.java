package daw2026.taller_contenedores.repository;

import daw2026.taller_contenedores.model.Coche;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
    Optional<Coche> findByMatricula(String matricula);
    List<Coche> findByMarca(String marca);
    List<Coche> findByModelo(String modelo);
}