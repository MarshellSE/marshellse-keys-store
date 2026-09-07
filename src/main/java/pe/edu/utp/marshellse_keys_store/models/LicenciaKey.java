package pe.edu.utp.marshellse_keys_store.models;

import jakarta.persistence.*;

@Entity
public class LicenciaKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long videojuegoId;
    private String codigo;
    private String regionActivacion;
    private Boolean estadoDisponible;

    public LicenciaKey() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVideojuegoId() {
        return videojuegoId;
    }

    public void setVideojuegoId(Long videojuegoId) {
        this.videojuegoId = videojuegoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRegionActivacion() {
        return regionActivacion;
    }

    public void setRegionActivacion(String regionActivacion) {
        this.regionActivacion = regionActivacion;
    }

    public Boolean getEstadoDisponible() {
        return estadoDisponible;
    }

    public void setEstadoDisponible(Boolean estadoDisponible) {
        this.estadoDisponible = estadoDisponible;
    }
}