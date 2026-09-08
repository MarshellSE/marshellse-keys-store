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
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // 5: GET /api/wallet/usuarios/{id}
    @Test
    public void getUsuario_Exito_RetornaPerfil() throws Exception {
        mockMvc.perform(get("/api/wallet/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias", is("Jordano")));
    }

    @Test
    public void getUsuario_Fallo_NoEncontrado() throws Exception {
        mockMvc.perform(get("/api/wallet/usuarios/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getUsuario_Validacion_IdInvalido() throws Exception {
        mockMvc.perform(get("/api/wallet/usuarios/IDFalso"))
                .andExpect(status().isBadRequest());
    }

    // 6: PUT /api/wallet/usuarios/{id}/recargar
    @Test
    public void recargar_Exito_AumentaSaldo() throws Exception {
        mockMvc.perform(put("/api/wallet/usuarios/1/recargar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("50.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saldoWallet", is(200.00)));
    }

    @Test
    public void recargar_Fallo_UsuarioInexistente() throws Exception {
        mockMvc.perform(put("/api/wallet/usuarios/999/recargar")
                .contentType(MediaType.APPLICATION_JSON)
                .content("50.00"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void recargar_Validacion_SinMonto() throws Exception {
        mockMvc.perform(put("/api/wallet/usuarios/1/recargar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    // 7: GET /api/wallet/usuarios/{id}/ordenes
    @Test
    public void getHistorial_Exito_VacioAlInicio() throws Exception {
        mockMvc.perform(get("/api/wallet/usuarios/1/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getHistorial_Exito_DespuesDeCompra() throws Exception {
        // Ejecutamos una compra primero
        mockMvc.perform(post("/api/wallet/comprar/1/1"));
        // Validamos el historial
        mockMvc.perform(get("/api/wallet/usuarios/1/ordenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void getHistorial_Validacion_IdString() throws Exception {
        mockMvc.perform(get("/api/wallet/usuarios/uno/ordenes"))
                .andExpect(status().isBadRequest());
    }

    // 8: POST /api/wallet/comprar/{usuarioId}/{juegoId}
    @Test
    public void comprar_Exito_GeneraOrden() throws Exception {
        mockMvc.perform(post("/api/wallet/comprar/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago", is("Wallet Saldo")));
    }

    @Test
    public void comprar_Fallo_JuegoSinStock() throws Exception {
        mockMvc.perform(post("/api/wallet/comprar/1/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void comprar_Fallo_UsuarioNoExiste() throws Exception {
        mockMvc.perform(post("/api/wallet/comprar/999/1"))
                .andExpect(status().isNotFound());
    }
}