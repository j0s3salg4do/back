package co.edu.uniandes.dse.butacaperfecta.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PagoDetailDTO extends PagoDTO
{
    private List<BoletoDTO> boletos = new ArrayList<>();
}
