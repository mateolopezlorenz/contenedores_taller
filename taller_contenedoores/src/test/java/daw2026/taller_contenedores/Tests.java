package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import daw2026.taller_contenedores.repository.*;
import daw2026.taller_contenedores.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class Tests {
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
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

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

    // Tests de aceptación

    @Test
    void testGetAllCochesEndpoint() throws Exception {
        mockMvc.perform(get("/coches")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateCocheEndpoint() throws Exception {
        Coche coche = new Coche("ACC001", "Tesla", "Model 3");
        mockMvc.perform(post("/coches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(coche)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllMecanicosEndpoint() throws Exception {
        mockMvc.perform(get("/mecanicos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateMecanicoEndpoint() throws Exception {
        Mecanico mecanico = new Mecanico("AccMec1", "Electrónica");
        mockMvc.perform(post("/mecanicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mecanico)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllReparacionesEndpoint() throws Exception {
        mockMvc.perform(get("/reparaciones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateReparacionEndpoint() throws Exception {
        Coche c = cocheService.save(new Coche("ACC002", "Hyundai", "i30"));
        Mecanico m = mecanicoService.save(new Mecanico("AccMec2", "General"));
        Reparacion reparacion = new Reparacion(c, m, LocalDate.now(), "Mantenimiento", 2, 100.0);
        mockMvc.perform(post("/reparaciones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reparacion)))
                .andExpect(status().isOk());
    }

}