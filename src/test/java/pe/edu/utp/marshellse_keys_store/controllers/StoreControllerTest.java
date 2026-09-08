package pe.edu.utp.marshellse_keys_store.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 1: GET /api/store/juegos
    @Test
    public void getJuegos_Retorna200Ok() throws Exception {
        mockMvc.perform(get("/api/store/juegos"))
                .andExpect(status().isOk());
    }

    @Test
    public void getJuegos_FormatoEsJson() throws Exception {
        mockMvc.perform(get("/api/store/juegos"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getJuegos_ContieneDatosSemilla() throws Exception {
        mockMvc.perform(get("/api/store/juegos"))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
    }

    // 2: GET /api/store/juegos/{id}
    @Test
    public void getJuegoById_Exito_RetornaJuego() throws Exception {
        mockMvc.perform(get("/api/store/juegos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("Grand Theft Auto V")));
    }

    @Test
    public void getJuegoById_Fallo_Retorna404() throws Exception {
        mockMvc.perform(get("/api/store/juegos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getJuegoById_Validacion_LetrasEnLugarDeId() throws Exception {
        mockMvc.perform(get("/api/store/juegos/abc"))
                .andExpect(status().isBadRequest());
    }

    // 3: POST /api/store/juegos
    @Test
    public void createJuego_Exito_Retorna200() throws Exception {
        String nuevoJuegoJson = "{\"titulo\":\"FIFA 24\", \"desarrollador\":\"EA\", \"precioBase\":69.99, \"stock\":10}";
        mockMvc.perform(post("/api/store/juegos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nuevoJuegoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", is("FIFA 24")));
    }

    @Test
    public void createJuego_Validacion_CuerpoVacio() throws Exception {
        mockMvc.perform(post("/api/store/juegos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createJuego_Validacion_TipoIncorrecto() throws Exception {
        mockMvc.perform(post("/api/store/juegos")
                .contentType(MediaType.TEXT_PLAIN)
                .content("Solo texto"))
                .andExpect(status().isUnsupportedMediaType());
    }

    // 4: GET /api/store/juegos/{id}/keys
    @Test
    public void getKeys_Exito_RetornaLista() throws Exception {
        mockMvc.perform(get("/api/store/juegos/1/keys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    public void getKeys_Exito_JuegoSinKeys() throws Exception {
        mockMvc.perform(get("/api/store/juegos/3/keys"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getKeys_Validacion_IdIncorrecto() throws Exception {
        mockMvc.perform(get("/api/store/juegos/xyz/keys"))
                .andExpect(status().isBadRequest());
    }
}