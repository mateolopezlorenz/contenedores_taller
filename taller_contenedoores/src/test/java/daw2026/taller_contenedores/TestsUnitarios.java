package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class TestsUnitarios {

    // Tests unitarios - Modelo Coche

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
    void testSetMarcaCoche() {
        Coche c = new Coche();
        c.setMarca("Honda");
        assertEquals("Honda", c.getMarca());
    }

    @Test
    void testSetModeloCoche() {
        Coche c = new Coche();
        c.setModelo("Civic");
        assertEquals("Civic", c.getModelo());
    }

    @Test
    void testCocheNoNulo() {
        Coche c = new Coche("5555EEE", "Audi", "A4");
        assertNotNull(c);
    }

    // Tests unitarios - Modelo Mecanico

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
    void testMecanicoNoNulo() {
        Mecanico m = new Mecanico("Carlos", "Motor");
        assertNotNull(m);
        assertEquals("Carlos", m.getNombre());
        assertEquals("Motor", m.getEspecialidad());
    }

    // Tests unitarios - Modelo Reparacion

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
    void testSetHorasReparacion() {
        Reparacion r = new Reparacion();
        r.setHoras(5);
        assertEquals(5, r.getHoras());
    }

    @Test
    void testSetPrecioReparacion() {
        Reparacion r = new Reparacion();
        r.setPrecio(150.0);
        assertEquals(150.0, r.getPrecio());
    }

    @Test
    void testReparacionNoNula() {
        Coche c = new Coche("TEST2", "Ford", "Fiesta");
        Mecanico m = new Mecanico("Ana", "Frenos");
        Reparacion r = new Reparacion(c, m, LocalDate.now(), "Mantenimiento", 3, 100.0);

        assertNotNull(r);
        assertNotNull(r.getCoche());
        assertNotNull(r.getMecanico());
    }

}