package co.edu.uniandes.dse.butacaperfecta.dto;

import lombok.Data;

@Data
public class AdministradorDTO {
  
    private Long id;
    private String nombreUsuario;
    private String correoUsuario;
    private String contrasenaUsuario;
    private TeatroDTO teatro;
    private ObraDeTeatroDTO obraDeTeatro;
    
}
