package co.edu.uniandes.dse.butacaperfecta.entities;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Entity
@Data
public class FuncionEntity extends BaseEntity {
    private Date fecha;
    private String hora;

    @PodamExclude
    @ManyToOne
    private ObraDeTeatroEntity obraDeTeatro;
    @PodamExclude
    @ManyToOne
    private TeatroEntity teatro;
    @PodamExclude
    @OneToMany(mappedBy = "funcion", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BoletoEntity> boletos = new ArrayList<>();
}
