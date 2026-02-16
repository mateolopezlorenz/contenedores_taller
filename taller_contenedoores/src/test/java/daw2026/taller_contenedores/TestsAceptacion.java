package daw2026.taller_contenedores;

import daw2026.taller_contenedores.model.*;
import daw2026.taller_contenedores.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestsAceptacion {
    @Autowired
    private CocheService cocheService;
    @Autowired
    private MecanicoService mecanicoService;
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

    // Tests de aceptación - Endpoints Coche

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

    // Tests de aceptación - Endpoints Mecanico

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

    // Tests de aceptación - Endpoints Reparacion

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

    // Tests de aceptación - Endpoints adicionales

    @Test
    void testGetCocheByIdEndpoint() throws Exception {
        Coche coche = new Coche("ACC003", "BMW", "Serie 3");
        Coche saved = cocheService.save(coche);
        mockMvc.perform(get("/coches/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateCocheEndpoint() throws Exception {
        Coche coche = new Coche("ACC004", "Mercedes", "C-Class");
        Coche saved = cocheService.save(coche);
        saved.setMarca("Mercedes Updated");
        mockMvc.perform(put("/coches/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCocheEndpoint() throws Exception {
        Coche coche = new Coche("ACC005", "Audi", "A6");
        Coche saved = cocheService.save(coche);
        mockMvc.perform(delete("/coches/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetMecanicoByIdEndpoint() throws Exception {
        Mecanico mecanico = new Mecanico("AccMec3", "Climatización");
        Mecanico saved = mecanicoService.save(mecanico);
        mockMvc.perform(get("/mecanicos/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}