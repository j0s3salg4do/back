package co.edu.uniandes.dse.butacaperfecta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class SillaEntity extends BaseEntity{
    private String fila;
    private int numero;
    private boolean disponible;
    private String ubicacion;

    @PodamExclude
    @OneToOne
    private BoletoEntity boleto;

    @PodamExclude
    @ManyToOne
    private TeatroEntity teatro;
    
}
