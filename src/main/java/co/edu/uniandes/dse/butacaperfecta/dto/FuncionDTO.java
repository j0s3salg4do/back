package co.edu.uniandes.dse.butacaperfecta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class FuncionDTO {
    
    private Long id;
    private Date fecha;
    private String hora;
    private ObraDeTeatroDTO obraDeTeatro;
    private String nombreTeatro;
    
}
