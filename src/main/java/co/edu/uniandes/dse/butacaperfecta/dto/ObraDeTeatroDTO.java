package co.edu.uniandes.dse.butacaperfecta.dto;

import lombok.Data;

@Data

public class ObraDeTeatroDTO {

    private Long id;
    private String titulo;
    private String genero;
    private Integer duracion;
    private String sinopsis;
    private String elenco;
    private String imagen;

}