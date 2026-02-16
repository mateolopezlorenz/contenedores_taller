package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import daw2026.taller_contenedores.repository.*;
import daw2026.taller_contenedores.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestsIntegracion {
    @Autowired
    private CocheService cocheService;
    @Autowired
    private MecanicoService mecanicoService;
    @Autowired
    private ReparacionService reparacionService;
    @Autowired
    private CocheRepository cocheRepository;
    @Autowired
    private MecanicoRepository mecanicoRepository;
    @Autowired
    private ReparacionRepository reparacionRepository;

    // Tests de integración - Servicios

    @Test
    void testSaveCocheService() {
        Coche c = new Coche("INT001", "BMW", "X5");
        Coche saved = cocheService.save(c);
        assertNotNull(saved.getId());
        assertEquals("INT001", saved.getMatricula());
    }

    @Test
    void testFindAllCochesService() {
        cocheService.save(new Coche("INT003", "Audi", "A4"));
        var coches = cocheService.findAll();
        assertNotNull(coches);
        assertFalse(coches.isEmpty());
    }

    @Test
    void testUpdateCocheService() {
        Coche c = new Coche("INT004", "Ford", "Focus");
        Coche saved = cocheService.save(c);
        saved.setMarca("Ford Updated");
        Coche updated = cocheService.save(saved);
        assertEquals("Ford Updated", updated.getMarca());
    }

    @Test
    void testSaveMecanicoService() {
        Mecanico m = new Mecanico("IntMec1", "Hidráulica");
        Mecanico saved = mecanicoService.save(m);
        assertNotNull(saved.getId());
        assertEquals("IntMec1", saved.getNombre());
    }

    @Test
    void testFindAllMecanicosService() {
        mecanicoService.save(new Mecanico("IntMec3", "Motor"));
        var mecanicos = mecanicoService.findAll();
        assertNotNull(mecanicos);
        assertFalse(mecanicos.isEmpty());
    }

    @Test
    void testSaveReparacionService() {
        Coche c = cocheService.save(new Coche("INT005", "Honda", "Civic"));
        Mecanico m = mecanicoService.save(new Mecanico("IntMec5", "General"));
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Mantenimiento", 2, 100.0);
        Reparacion saved = reparacionService.save(r);
        assertNotNull(saved.getId());
        assertEquals("Mantenimiento", saved.getDescripcion());
    }

    @Test
    void testFindReparacionByIdService() {
        Coche c = cocheService.save(new Coche("INT006", "Volkswagen", "Golf"));
        Mecanico m = mecanicoService.save(new Mecanico("IntMec6", "Transmisión"));
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Revisión", 3, 150.0);
        Reparacion saved = reparacionService.save(r);
        var found = reparacionService.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("Revisión", found.get().getDescripcion());
    }

    @Test
    void testUpdateReparacionService() {
        Coche c = cocheService.save(new Coche("INT008", "Seat", "Ibiza"));
        Mecanico m = mecanicoService.save(new Mecanico("IntMec8", "Suspensión"));
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Revisión", 2, 80.0);
        Reparacion saved = reparacionService.save(r);
        saved.setDescripcion("Revisión Completa");
        Reparacion updated = reparacionService.save(saved);
        assertEquals("Revisión Completa", updated.getDescripcion());
    }

    // Tests de integración - Repositorios

    @Test
    void testSaveCocheRepository() {
        Coche c = new Coche("REPO001", "Porsche", "911");
        Coche saved = cocheRepository.save(c);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindCocheByIdRepository() {
        Coche c = new Coche("REPO002", "Lamborghini", "Huracán");
        Coche saved = cocheRepository.save(c);
        var found = cocheRepository.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void testSaveMecanicoRepository() {
        Mecanico m = new Mecanico("RepoMec1", "Premium");
        Mecanico saved = mecanicoRepository.save(m);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindMecanicoByIdRepository() {
        Mecanico m = new Mecanico("RepoMec2", "Lujo");
        Mecanico saved = mecanicoRepository.save(m);
        var found = mecanicoRepository.findById(saved.getId());
        assertTrue(found.isPresent());
    }

    @Test
    void testSaveReparacionRepository() {
        Coche c = cocheRepository.save(new Coche("REPO004", "Maserati", "Quattroporte"));
        Mecanico m = mecanicoRepository.save(new Mecanico("RepoMec4", "Elegancia"));
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Mantenimiento", 5, 500.0);
        Reparacion saved = reparacionRepository.save(r);
        assertNotNull(saved.getId());
    }

    @Test
    void testFindReparacionByIdRepository() {
        Coche c = cocheRepository.save(new Coche("REPO005", "Bugatti", "Chiron"));
        Mecanico m = mecanicoRepository.save(new Mecanico("RepoMec5", "Hiperluxo"));
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Revisión", 6, 600.0);
        Reparacion saved = reparacionRepository.save(r);
        var found = reparacionRepository.findById(saved.getId());
        assertTrue(found.isPresent());
    }

}
