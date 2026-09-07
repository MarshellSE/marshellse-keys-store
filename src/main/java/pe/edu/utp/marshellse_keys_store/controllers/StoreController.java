package pe.edu.utp.marshellse_keys_store.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.utp.marshellse_keys_store.models.LicenciaKey;
import pe.edu.utp.marshellse_keys_store.models.Videojuego;
import pe.edu.utp.marshellse_keys_store.repositories.LicenciaKeyRepository;
import pe.edu.utp.marshellse_keys_store.repositories.VideojuegoRepository;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreController {

    private final VideojuegoRepository videojuegoRepo;
    private final LicenciaKeyRepository keyRepo;

    public StoreController(VideojuegoRepository videojuegoRepo, LicenciaKeyRepository keyRepo) {
        this.videojuegoRepo = videojuegoRepo;
        this.keyRepo = keyRepo;
    }

    // Listar todos los juegos del catalogo
    @GetMapping("/juegos")
    public List<Videojuego> getJuegos() {
        return videojuegoRepo.findAll();
    }

    // Buscar un juego por su ID
    @GetMapping("/juegos/{id}")
    public ResponseEntity<Videojuego> getJuegoById(@PathVariable Long id) {
        return videojuegoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Agregar un nuevo juego al catalogo
    @PostMapping("/juegos")
    public Videojuego createJuego(@RequestBody Videojuego nuevoJuego) {
        return videojuegoRepo.save(nuevoJuego);
    }

    // Ver las keys disponibles de un juego específico
    @GetMapping("/juegos/{id}/keys")
    public List<LicenciaKey> getKeysDisponibles(@PathVariable Long id) {
        return keyRepo.findByVideojuegoIdAndEstadoDisponibleTrue(id);
    }
}