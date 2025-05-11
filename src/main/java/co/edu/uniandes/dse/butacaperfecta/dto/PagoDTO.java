package co.edu.uniandes.dse.butacaperfecta.dto;

import lombok.Data;

@Data
public class PagoDTO {
    private Long id;
    private String metodoPago;
    private Double monto;
}
