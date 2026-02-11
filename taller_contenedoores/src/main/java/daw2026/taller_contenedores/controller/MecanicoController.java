package daw2026.taller_contenedores.controller;

import daw2026.taller_contenedores.model.Mecanico;
import daw2026.taller_contenedores.service.MecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mecanicos")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    public MecanicoController(MecanicoService mecanicoService) {
        this.mecanicoService = mecanicoService;
    }

    @GetMapping
    public List<Mecanico> getAllMecanicos() {
        return mecanicoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> getMecanicoById(@PathVariable Long id) {
        return mecanicoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Mecanico createMecanico(@RequestBody Mecanico mecanico) {
        return mecanicoService.save(mecanico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> updateMecanico(@PathVariable Long id, @RequestBody Mecanico mecanicoDetails) {
        return mecanicoService.findById(id)
                .map(mecanico -> {
                    mecanico.setNombre(mecanicoDetails.getNombre());
                    mecanico.setEspecialidad(mecanicoDetails.getEspecialidad());
                    return ResponseEntity.ok(mecanicoService.save(mecanico));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMecanico(@PathVariable Long id) {
        if (mecanicoService.findById(id).isPresent()) {
            mecanicoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}