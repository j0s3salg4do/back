package co.edu.uniandes.dse.butacaperfecta.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity
public class ClienteEntity extends BaseEntity {

    private String nombreUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    private Boolean tieneSubscripcion;

    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BoletoEntity> boletas = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ResenaEntity> resenas = new ArrayList<>();

    //@PodamExclude
    //@OneToOne
}

