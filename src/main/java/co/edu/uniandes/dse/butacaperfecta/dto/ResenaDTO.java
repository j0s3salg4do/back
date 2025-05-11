package co.edu.uniandes.dse.butacaperfecta.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResenaDTO {

    private Long id;
    private String comentario;
    private Integer calificacion;
    private TeatroDTO teatro;
    private ObraDeTeatroDTO obraDeTeatro;
    private ClienteDTO cliente;
    
}
