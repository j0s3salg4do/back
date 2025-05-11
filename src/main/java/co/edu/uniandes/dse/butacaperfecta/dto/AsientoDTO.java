package co.edu.uniandes.dse.butacaperfecta.dto;

import lombok.Data;

@Data
public class AsientoDTO {

    private Long id;
    private String fila;
    private Integer numero;
    private boolean disponible;
    private String nombreTeatro;

}
