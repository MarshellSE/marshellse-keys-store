package pe.edu.utp.marshellse_keys_store.models;

import jakarta.persistence.*;

@Entity
public class UsuarioGamer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alias;
    private String email;
    private Double saldoWallet;
    private Boolean cuentaActiva;

    public UsuarioGamer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSaldoWallet() {
        return saldoWallet;
    }

    public void setSaldoWallet(Double saldoWallet) {
        this.saldoWallet = saldoWallet;
    }

    public Boolean getCuentaActiva() {
        return cuentaActiva;
    }

    public void setCuentaActiva(Boolean cuentaActiva) {
        this.cuentaActiva = cuentaActiva;
    }
}