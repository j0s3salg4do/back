package co.edu.uniandes.dse.butacaperfecta.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
@Entity
@Data
public class BoletoEntity extends BaseEntity {
    private Double precio;
    @PodamExclude
    @ManyToOne
    private FuncionEntity funcion;
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    @PodamExclude
    @OneToOne
    private AsientoEntity silla;
    @PodamExclude
    @ManyToOne
    private PagoEntity pago;
    @PodamExclude
    @ManyToOne
    private EventoEspecialEntity eventoEspecial;
    
}
