package daw2026.taller_contenedores.controller;

import daw2026.taller_contenedores.model.Coche;
import daw2026.taller_contenedores.service.CocheService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coches")
public class CocheController {

    private final CocheService cocheService;

    public CocheController(CocheService cocheService) {
        this.cocheService = cocheService;
    }

    @GetMapping
    public List<Coche> getAllCoches() {
        return cocheService.findAll();
    }

    @PostMapping
    public Coche crearCoche(@RequestBody Coche coche) {
        return cocheService.save(coche);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coche> getCocheById(@PathVariable Long id) {
        return cocheService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<Coche> getCocheByMatricula(@PathVariable String matricula) {
        return cocheService.findByMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Coche> updateCoche(@PathVariable Long id, @RequestBody Coche cocheDetails) {
        return cocheService.findById(id)
                .map(coche -> {
                    coche.setMatricula(cocheDetails.getMatricula());
                    coche.setMarca(cocheDetails.getMarca());
                    coche.setModelo(cocheDetails.getModelo());
                    return ResponseEntity.ok(cocheService.save(coche));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoche(@PathVariable Long id) {
        if (cocheService.findById(id).isPresent()) {
            cocheService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}