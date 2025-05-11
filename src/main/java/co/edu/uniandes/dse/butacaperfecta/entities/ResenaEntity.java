package co.edu.uniandes.dse.butacaperfecta.entities;

import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ResenaEntity extends BaseEntity {
    
    private String comentario;

    @PodamExclude
    private Integer calificacion;

    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
    @PodamExclude
    @ManyToOne
    private ObraDeTeatroEntity obraDeTeatro;
    @PodamExclude
    @ManyToOne
    private TeatroEntity teatro;

    public void validarResena() throws IllegalOperationException {
        if (obraDeTeatro != null && teatro != null) {
            throw new IllegalOperationException("La reseña no puede estar vinculada a una obra de teatro y a un teatro.");
        }
        if (obraDeTeatro == null && teatro == null) {
            throw new IllegalOperationException("La reseña debe estar vinculada a una obra de teatro o a un teatro.");
        }
        if (calificacion < 0 || calificacion > 5) {
            throw new IllegalOperationException("La calificación de la reseña debe estar entre 0 y 5.");
        }

    }

}
