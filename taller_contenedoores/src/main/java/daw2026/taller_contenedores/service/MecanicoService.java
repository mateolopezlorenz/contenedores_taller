package daw2026.taller_contenedores.service;

import daw2026.taller_contenedores.model.Mecanico;
import daw2026.taller_contenedores.repository.MecanicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    public MecanicoService(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    public List<Mecanico> findAll() {
        return mecanicoRepository.findAll();
    }

    public Optional<Mecanico> findById(Long id) {
        return mecanicoRepository.findById(id);
    }

    public Mecanico save(Mecanico mecanico) {
        return mecanicoRepository.save(mecanico);
    }

    public void deleteById(Long id) {
        mecanicoRepository.deleteById(id);
    }
}