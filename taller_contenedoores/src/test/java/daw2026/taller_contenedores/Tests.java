package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class Tests {

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
}
