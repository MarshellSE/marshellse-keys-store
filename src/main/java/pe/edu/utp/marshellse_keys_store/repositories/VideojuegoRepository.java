package pe.edu.utp.marshellse_keys_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marshellse_keys_store.models.Videojuego;

public interface VideojuegoRepository extends JpaRepository<Videojuego, Long> {
}