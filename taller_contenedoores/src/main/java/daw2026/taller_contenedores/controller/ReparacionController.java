package daw2026.taller_contenedores.controller;

import daw2026.taller_contenedores.model.Reparacion;
import daw2026.taller_contenedores.service.ReparacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reparaciones")
public class ReparacionController {

    private final ReparacionService reparacionService;

    public ReparacionController(ReparacionService reparacionService) {
        this.reparacionService = reparacionService;
    }

    @GetMapping
    public List<Reparacion> getAllReparaciones() {
        return reparacionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reparacion> getReparacionById(@PathVariable Long id) {
        return reparacionService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/coche/{id}")
    public List<Reparacion> getReparacionesByCoche(@PathVariable Long id) {
        return reparacionService.findByCocheId(id);
    }

    @GetMapping("/mecanico/{id}")
    public List<Reparacion> getReparacionesByMecanico(@PathVariable Long id) {
        return reparacionService.findByMecanicoId(id);
    }

    @PostMapping
    public Reparacion createReparacion(@RequestBody Reparacion reparacion) {
        return reparacionService.save(reparacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reparacion> updateReparacion(@PathVariable Long id, @RequestBody Reparacion reparacionDetails) {
        return reparacionService.findById(id)
                .map(reparacion -> {
                    reparacion.setCoche(reparacionDetails.getCoche());
                    reparacion.setMecanico(reparacionDetails.getMecanico());
                    reparacion.setFecha(reparacionDetails.getFecha());
                    reparacion.setDescripcion(reparacionDetails.getDescripcion());
                    reparacion.setHoras(reparacionDetails.getHoras());
                    reparacion.setPrecio(reparacionDetails.getPrecio());
                    return ResponseEntity.ok(reparacionService.save(reparacion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReparacion(@PathVariable Long id) {
        if (reparacionService.findById(id).isPresent()) {
            reparacionService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}