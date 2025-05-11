package co.edu.uniandes.dse.butacaperfecta.dto;

import java.util.Date;

import lombok.Data;

@Data

public class EventoEspecialDTO {

    private Long id;
    private String nombreEvento;
    private String descripcionEvento;
    private Date fecha;
    
}
