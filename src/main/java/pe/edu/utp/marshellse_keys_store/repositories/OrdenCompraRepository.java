package pe.edu.utp.marshellse_keys_store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.utp.marshellse_keys_store.models.OrdenCompra;
import java.util.List;

public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
    // Consulta para ver el historial de un usuario
    List<OrdenCompra> findByUsuarioId(Long usuarioId);
}