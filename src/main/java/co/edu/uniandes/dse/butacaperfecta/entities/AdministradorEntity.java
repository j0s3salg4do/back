package co.edu.uniandes.dse.butacaperfecta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class AdministradorEntity extends BaseEntity {

    private String nombreUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;

    @PodamExclude
    @OneToOne
    private TeatroEntity teatro;

    @PodamExclude
    @OneToOne
    @JoinColumn(name = "obra_id")
    private ObraDeTeatroEntity obraDeTeatro;
}
