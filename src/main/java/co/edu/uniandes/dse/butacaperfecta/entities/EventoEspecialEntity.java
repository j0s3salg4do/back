package co.edu.uniandes.dse.butacaperfecta.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EventoEspecialEntity extends BaseEntity {
    private String nombreEvento;
    private String descripcionEvento;
    private Date fecha;


    // Un evento especial está asociado a una sola obra de teatro.
    @PodamExclude
    @ManyToOne
    private TeatroEntity teatro;

    //Un evento especial puede tener múltiples boletos asociados.
    @PodamExclude
    @OneToMany(mappedBy = "eventoEspecial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoletoEntity> boletos = new ArrayList<>();
}
