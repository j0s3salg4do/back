package co.edu.uniandes.dse.butacaperfecta.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ObraDeTeatroEntity extends BaseEntity {
    
    private String titulo;
    private String genero;
    
    private Integer duracion;
    private String sinopsis;
    private String elenco;

    @Column(length = 500)
    private String imagen;


    
    // Una obra de teatro puede tener múltiples reseñas.
    // Una obra de teatro puede tener una o varias funciones.

    @PodamExclude
    @OneToMany(mappedBy = "obraDeTeatro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FuncionEntity> funciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "obraDeTeatro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ResenaEntity> resenas = new ArrayList<>();

}



