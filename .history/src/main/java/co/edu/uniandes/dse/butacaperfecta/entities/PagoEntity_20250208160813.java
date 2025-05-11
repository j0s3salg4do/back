package co.edu.uniandes.dse.butacaperfecta.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PagoEntity extends BaseEntity{

    private String metodoPago;
    private Double monto;

    @PodamExclude
    @OneToMany(mappedBy = "Pago", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BoletoEntity> boletos;
    
}
