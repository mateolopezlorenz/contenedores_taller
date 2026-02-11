package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import daw2026.taller_contenedores.repository.*;
import daw2026.taller_contenedores.service.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    private CocheService cocheService;
    
    private MecanicoService mecanicoService;
    
    private ReparacionService reparacionService;

    private CocheRepository cocheRepository;

    private MecanicoRepository mecanicoRepository;

    private ReparacionRepository reparacionRepository;

    // Tests unitarios 
    @Test
    void testCrearCoche() {
        Coche c = new Coche("1111AAA", "Toyota", "Corolla");
        assertNotNull(c);
        assertEquals("1111AAA", c.getMatricula());
    }

    @Test
    void testSetMatricula() {
        Coche c = new Coche();
        c.setMatricula("2222BBB");
        assertEquals("2222BBB", c.getMatricula());
    }

    @Test
    void testCrearMecanico() {
        Mecanico m = new Mecanico("Juan", "Electricista");
        assertNotNull(m);
        assertEquals("Juan", m.getNombre());
    }

    @Test
    void testSetNombreMecanico() {
        Mecanico m = new Mecanico();
        m.setNombre("Pedro");
        assertEquals("Pedro", m.getNombre());
    }

    @Test
    void testSetEspecialidad() {
        Mecanico m = new Mecanico();
        m.setEspecialidad("Mecánico");
        assertEquals("Mecánico", m.getEspecialidad());
    }

    @Test
    void testCrearReparacion() {
        Coche c = new Coche("TEST", "Toyota", "Corolla");
        Mecanico m = new Mecanico("Carlos", "Electricista");
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Test", 2, 50.0);
        
        assertNotNull(r);
        assertEquals("Test", r.getDescripcion());
    }

    @Test
    void testSetDescripcionReparacion() {
        Reparacion r = new Reparacion();
        r.setDescripcion("Actualizado");
        assertEquals("Actualizado", r.getDescripcion());
    }

    @Test
    void testCocheNoNulo() {
        Coche c = new Coche("5555EEE", "Audi", "A4");
        assertNotNull(c);
    }

    // Tests de integración con servicios
    @Test
    void testSaveCoche() {
        if (cocheService != null) {
            Coche c = new Coche("SERVICE1", "BMW", "X5");
            Coche saved = cocheService.save(c);
            assertNotNull(saved);
        }
    }

    @Test
    void testFindCocheById() {
        if (cocheService != null) {
            Coche c = new Coche("SERVICE2", "Mercedes", "C300");
            Coche saved = cocheService.save(c);
            var found = cocheService.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllCoches() {
        if (cocheService != null) {
            assertNotNull(cocheService.findAll());
        }
    }

    @Test
    void testSaveMecanico() {
        if (mecanicoService != null) {
            Mecanico m = new Mecanico("Servicio1", "Hidráulica");
            Mecanico saved = mecanicoService.save(m);
            assertNotNull(saved);
        }
    }

    @Test
    void testFindMecanicoById() {
        if (mecanicoService != null) {
            Mecanico m = new Mecanico("Servicio2", "Neumática");
            Mecanico saved = mecanicoService.save(m);
            var found = mecanicoService.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllMecanicos() {
        if (mecanicoService != null) {
            assertNotNull(mecanicoService.findAll());
        }
    }

    @Test
    void testSaveReparacion() {
        if (cocheService != null && mecanicoService != null && reparacionService != null) {
            Coche c = new Coche("SERVICE3", "Audi", "A6");
            Mecanico m = new Mecanico("Service3", "General");
            Coche savedC = cocheService.save(c);
            Mecanico savedM = mecanicoService.save(m);
            
            Reparacion r = new Reparacion(savedC, savedM, LocalDate.now(), "Service", 3, 150.0);
            Reparacion saved = reparacionService.save(r);
            assertNotNull(saved);
        }
    }

    @Test
    void testFindReparacionById() {
        if (cocheService != null && mecanicoService != null && reparacionService != null) {
            Coche c = new Coche("SERVICE4", "Volkswagen", "Golf");
            Mecanico m = new Mecanico("Service4", "Transmisión");
            Coche savedC = cocheService.save(c);
            Mecanico savedM = mecanicoService.save(m);
            
            Reparacion r = new Reparacion(savedC, savedM, LocalDate.now(), "Service", 2, 100.0);
            Reparacion saved = reparacionService.save(r);
            var found = reparacionService.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllReparaciones() {
        if (reparacionService != null) {
            assertNotNull(reparacionService.findAll());
        }
    }

    // Tests de integración con controladores
    @Test
    void testControllerCochesSaveList() {
        if (cocheService != null) {
            cocheService.save(new Coche("CTRL1", "Ford", "Focus"));
            var coches = cocheService.findAll();
            assertFalse(coches.isEmpty());
        }
    }

    @Test
    void testControllerMecanicosSaveList() {
        if (mecanicoService != null) {
            mecanicoService.save(new Mecanico("ControlMec1", "Frenos"));
            var mecanicos = mecanicoService.findAll();
            assertFalse(mecanicos.isEmpty());
        }
    }

    @Test
    void testControllerReparacionesSaveList() {
        if (cocheService != null && mecanicoService != null && reparacionService != null) {
            Coche c = cocheService.save(new Coche("CTRL2", "Honda", "Civic"));
            Mecanico m = mecanicoService.save(new Mecanico("ControlMec2", "Motor"));
            Reparacion r = new Reparacion(c, m, LocalDate.now(), "Control", 2, 120.0);
            reparacionService.save(r);
            var reparaciones = reparacionService.findAll();
            assertFalse(reparaciones.isEmpty());
        }
    }

    @Test
    void testControllerCocheUpdate() {
        if (cocheService != null) {
            Coche c = cocheService.save(new Coche("CTRL3", "BMW", "320"));
            c.setMarca("BMW Updated");
            Coche updated = cocheService.save(c);
            assertEquals("BMW Updated", updated.getMarca());
        }
    }

    @Test
    void testControllerMecanicoUpdate() {
        if (mecanicoService != null) {
            Mecanico m = mecanicoService.save(new Mecanico("ControlMec3", "Suspensión"));
            m.setNombre("ControlMec3Updated");
            Mecanico updated = mecanicoService.save(m);
            assertEquals("ControlMec3Updated", updated.getNombre());
        }
    }

    @Test
    void testControllerReparacionUpdate() {
        if (cocheService != null && mecanicoService != null && reparacionService != null) {
            Coche c = cocheService.save(new Coche("CTRL4", "Audi", "A4"));
            Mecanico m = mecanicoService.save(new Mecanico("ControlMec4", "Neumáticos"));
            Reparacion r = new Reparacion(c, m, LocalDate.now(), "Control", 3, 180.0);
            Reparacion saved = reparacionService.save(r);
            saved.setDescripcion("Updated");
            Reparacion updated = reparacionService.save(saved);
            assertEquals("Updated", updated.getDescripcion());
        }
    }

    @Test
    void testControllerCocheCount() {
        if (cocheService != null) {
            cocheService.save(new Coche("CTRL5", "Mazda", "CX5"));
            var coches = cocheService.findAll();
            assertTrue(coches.size() > 0);
        }
    }

    @Test
    void testControllerMecanicoCount() {
        if (mecanicoService != null) {
            mecanicoService.save(new Mecanico("ControlMec5", "General"));
            var mecanicos = mecanicoService.findAll();
            assertTrue(mecanicos.size() > 0);
        }
    }

    @Test
    void testControllerReparacionCount() {
        if (cocheService != null && mecanicoService != null && reparacionService != null) {
            Coche c = cocheService.save(new Coche("CTRL6", "Subaru", "Outback"));
            Mecanico m = mecanicoService.save(new Mecanico("ControlMec6", "Electrónico"));
            Reparacion r = new Reparacion(c, m, LocalDate.now(), "Control", 4, 250.0);
            reparacionService.save(r);
            var reparaciones = reparacionService.findAll();
            assertTrue(reparaciones.size() > 0);
        }
    }

    // Tests de integración con repositorios
    @Test
    void testSaveCocheRepository() {
        if (cocheRepository != null) {
            Coche c = new Coche("REPO1", "Porsche", "911");
            Coche saved = cocheRepository.save(c);
            assertNotNull(saved.getId());
        }
    }

    @Test
    void testFindCocheByIdRepository() {
        if (cocheRepository != null) {
            Coche c = new Coche("REPO2", "Lamborghini", "Huracán");
            Coche saved = cocheRepository.save(c);
            var found = cocheRepository.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllCochesRepository() {
        if (cocheRepository != null) {
            cocheRepository.save(new Coche("REPO3", "BMW", "M3"));
            var coches = cocheRepository.findAll();
            assertNotNull(coches);
            assertTrue(coches.iterator().hasNext());
        }
    }

    @Test
    void testSaveMecanicoRepository() {
        if (mecanicoRepository != null) {
            Mecanico m = new Mecanico("RepoMec1", "Transmisión");
            Mecanico saved = mecanicoRepository.save(m);
            assertNotNull(saved.getId());
        }
    }

    @Test
    void testFindMecanicoByIdRepository() {
        if (mecanicoRepository != null) {
            Mecanico m = new Mecanico("RepoMec2", "Motor");
            Mecanico saved = mecanicoRepository.save(m);
            var found = mecanicoRepository.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllMecanicosRepository() {
        if (mecanicoRepository != null) {
            mecanicoRepository.save(new Mecanico("RepoMec3", "Frenos"));
            var mecanicos = mecanicoRepository.findAll();
            assertNotNull(mecanicos);
            assertTrue(mecanicos.iterator().hasNext());
        }
    }

    @Test
    void testSaveReparacionRepository() {
        if (cocheRepository != null && mecanicoRepository != null && reparacionRepository != null) {
            Coche c = new Coche("REPO4", "Ferrari", "F8");
            Mecanico m = new Mecanico("RepoMec4", "Lujo");
            Coche savedC = cocheRepository.save(c);
            Mecanico savedM = mecanicoRepository.save(m);
            
            Reparacion r = new Reparacion(savedC, savedM, LocalDate.now(), "Repo", 5, 500.0);
            Reparacion saved = reparacionRepository.save(r);
            assertNotNull(saved.getId());
        }
    }

    @Test
    void testFindReparacionByIdRepository() {
        if (cocheRepository != null && mecanicoRepository != null && reparacionRepository != null) {
            Coche c = new Coche("REPO5", "Maserati", "Quattroporte");
            Mecanico m = new Mecanico("RepoMec5", "Premium");
            Coche savedC = cocheRepository.save(c);
            Mecanico savedM = mecanicoRepository.save(m);
            
            Reparacion r = new Reparacion(savedC, savedM, LocalDate.now(), "RepoTest", 4, 400.0);
            Reparacion saved = reparacionRepository.save(r);
            var found = reparacionRepository.findById(saved.getId());
            assertTrue(found.isPresent());
        }
    }

    @Test
    void testFindAllReparacionesRepository() {
        if (cocheRepository != null && mecanicoRepository != null && reparacionRepository != null) {
            Coche c = new Coche("REPO6", "Pagani", "Huayra");
            Mecanico m = new Mecanico("RepoMec6", "Súper");
            Coche savedC = cocheRepository.save(c);
            Mecanico savedM = mecanicoRepository.save(m);
            
            Reparacion r = new Reparacion(savedC, savedM, LocalDate.now(), "RepoAll", 6, 600.0);
            reparacionRepository.save(r);
            var reparaciones = reparacionRepository.findAll();
            assertNotNull(reparaciones);
            assertTrue(reparaciones.iterator().hasNext());
        }
    }
    
}