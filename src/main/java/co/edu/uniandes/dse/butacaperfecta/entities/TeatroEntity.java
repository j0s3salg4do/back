package co.edu.uniandes.dse.butacaperfecta.entities;

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
public class TeatroEntity extends BaseEntity {
    
    private String nombreTeatro;
    private String direccionTeatro;
    private int capacidad;

    @PodamExclude
    @OneToOne
    private AdministradorEntity administrador;
    @OneToMany(mappedBy = "teatro", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<FuncionEntity> funciones;
    @OneToMany(mappedBy = "teatro", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<ResenaEntity> resenas;
    @OneToMany(mappedBy = "teatro", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) 
    private List<AsientoEntity> asientos;
    @OneToMany(mappedBy = "teatro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EventoEspecialEntity> eventosEspeciales;

    @PodamExclude
    private Integer capacidadTeatro;

    public void eliminarResenas() {
        this.resenas.clear();
    }

    public void eliminarFunciones() {
        this.funciones.clear();
    }
}
