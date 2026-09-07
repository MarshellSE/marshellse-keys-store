package pe.edu.utp.marshellse_keys_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marshellse_keys_store.models.LicenciaKey;
import java.util.List;

public interface LicenciaKeyRepository extends JpaRepository<LicenciaKey, Long> {
    // Consulta para buscar keys disponibles de un juego específico
    List<LicenciaKey> findByVideojuegoIdAndEstadoDisponibleTrue(Long videojuegoId);
}