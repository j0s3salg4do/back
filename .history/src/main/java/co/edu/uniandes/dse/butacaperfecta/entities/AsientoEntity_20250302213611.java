package co.edu.uniandes.dse.butacaperfecta.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "teatro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AsientoEntity> asientos = new ArrayList<>();
    
}
