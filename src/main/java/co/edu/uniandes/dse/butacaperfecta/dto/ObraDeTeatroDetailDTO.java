package co.edu.uniandes.dse.butacaperfecta.dto;

import co.edu.uniandes.dse.butacaperfecta.dto.ResenaDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data

public class ObraDeTeatroDetailDTO  extends ObraDeTeatroDTO{

    private List<ResenaDTO> resenas = new ArrayList<>();
    private List<FuncionDTO> funciones = new ArrayList<>();
    
}