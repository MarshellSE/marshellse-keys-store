package pe.edu.utp.marshellse_keys_store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marshellse_keys_store.models.OrdenCompra;
import pe.edu.utp.marshellse_keys_store.models.UsuarioGamer;
import pe.edu.utp.marshellse_keys_store.models.Videojuego;
import pe.edu.utp.marshellse_keys_store.models.LicenciaKey;
import pe.edu.utp.marshellse_keys_store.models.RecargaRequest;
import pe.edu.utp.marshellse_keys_store.repositories.OrdenCompraRepository;
import pe.edu.utp.marshellse_keys_store.repositories.UsuarioGamerRepository;
import pe.edu.utp.marshellse_keys_store.repositories.VideojuegoRepository;
import pe.edu.utp.marshellse_keys_store.repositories.LicenciaKeyRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final UsuarioGamerRepository usuarioRepo;
    private final OrdenCompraRepository ordenRepo;
    private final VideojuegoRepository videojuegoRepo;
    private final LicenciaKeyRepository keyRepo;

    public WalletController(UsuarioGamerRepository usuarioRepo, OrdenCompraRepository ordenRepo,
            VideojuegoRepository videojuegoRepo, LicenciaKeyRepository keyRepo) {
        this.usuarioRepo = usuarioRepo;
        this.ordenRepo = ordenRepo;
        this.videojuegoRepo = videojuegoRepo;
        this.keyRepo = keyRepo;
    }

    // Ver el perfil y saldo del usuario
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioGamer> getUsuario(@PathVariable Long id) {
        return usuarioRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Recargar saldo en la billetera
    @PutMapping("/usuarios/{id}/recargar")
    public ResponseEntity<UsuarioGamer> recargarWallet(@PathVariable Long id, @RequestBody RecargaRequest request) {
        return usuarioRepo.findById(id)
                .map(usuario -> {
                    usuario.setSaldoWallet(usuario.getSaldoWallet() + request.getMonto());
                    return ResponseEntity.ok(usuarioRepo.save(usuario));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Ver historial de compras de un usuario
    @GetMapping("/usuarios/{id}/ordenes")
    public List<OrdenCompra> getHistorial(@PathVariable Long id) {
        return ordenRepo.findByUsuarioId(id);
    }

    // Comprar un juego
    @PostMapping("/comprar/{usuarioId}/{juegoId}")
    public ResponseEntity<OrdenCompra> comprarJuego(@PathVariable Long usuarioId, @PathVariable Long juegoId) {
        UsuarioGamer usuario = usuarioRepo.findById(usuarioId).orElse(null);
        Videojuego juego = videojuegoRepo.findById(juegoId).orElse(null);

        if (usuario == null || juego == null)
            return ResponseEntity.notFound().build();
        if (usuario.getSaldoWallet() < juego.getPrecioBase())
            return ResponseEntity.badRequest().build(); // Sin saldo

        List<LicenciaKey> keysDisponibles = keyRepo.findByVideojuegoIdAndEstadoDisponibleTrue(juegoId);
        if (keysDisponibles.isEmpty())
            return ResponseEntity.badRequest().build(); // Sin stock

        // Logica de transaccion
        LicenciaKey keyAsignada = keysDisponibles.get(0);
        keyAsignada.setEstadoDisponible(false);
        keyRepo.save(keyAsignada);

        usuario.setSaldoWallet(usuario.getSaldoWallet() - juego.getPrecioBase());
        usuarioRepo.save(usuario);

        juego.setStock(juego.getStock() - 1);
        videojuegoRepo.save(juego);

        OrdenCompra orden = new OrdenCompra();
        orden.setUsuarioId(usuario.getId());
        orden.setMetodoPago("Wallet Saldo");
        orden.setMontoTotal(juego.getPrecioBase());
        orden.setFechaTransaccion(LocalDateTime.now());

        return ResponseEntity.ok(ordenRepo.save(orden));
    }
}