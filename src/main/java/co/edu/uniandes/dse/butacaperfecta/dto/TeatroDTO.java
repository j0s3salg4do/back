package co.edu.uniandes.dse.butacaperfecta.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeatroDTO {

    private Long id;
    private String nombreTeatro;
    private String direccionTeatro;
    private int capacidad;

    private List<FuncionDTO> funciones;
    private List<AsientoDTO> asientos;
    private List<EventoEspecialDTO> eventosEspeciales;
    
}
