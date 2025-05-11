package co.edu.uniandes.dse.butacaperfecta.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class AsientoEntity extends BaseEntity{
    private String fila;

    
    private Integer numero;
    private boolean disponible;
    private String ubicacion;

    @PodamExclude
    @OneToOne
    private BoletoEntity boleto;

    @PodamExclude
    @ManyToOne
    @JsonBackReference
    private TeatroEntity teatro;
    
}
