package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.butacaperfecta.dto.AsientoDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.services.AsientoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/teatros")
public class AsientoController {
    @Autowired
    private AsientoService asientoService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{teatroId}/asientos")
    @ResponseStatus(code = HttpStatus.OK)
    
    /**
	 * Busca y devuelve todas los asientos que existen en un teatro.
	 *
	 * @param teatroId El ID del teatro del cual se buscan los asientos
	 * @return JSONArray {@link AsientoDTO} - Los asientos encontrados en el teatro. Si
	 *         no hay ninguna retorna una lista vacía.
	 */
    public List<AsientoDTO> getAsientos(@PathVariable Long teatroId) throws EntityNotFoundException
    {
        List<AsientoEntity> asientos = asientoService.getAsientos(teatroId);
        return asientos.stream()
                .map(asiento -> modelMapper.map(asiento, AsientoDTO.class))
                .collect(Collectors.toList());
        
    }

    
    

}
