package co.edu.uniandes.dse.butacaperfecta.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoletoDTO {
    
    private Long id;
    private Double precio;
    private String nombreTeatro;
    private String cliente;
    private String silla;
    private String tituloObra;
    private String filaSilla;
    private Integer numeroSilla;
    private Date fecha;

}
