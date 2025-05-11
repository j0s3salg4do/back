package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.butacaperfecta.dto.ResenaDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.ResenaService;

@RestController
@RequestMapping("/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ResenaDTO> getResenas() throws EntityNotFoundException {
        List<ResenaEntity> resenas = resenaService.obtenerResenas();
        return modelMapper.map(resenas, new TypeToken<List<ResenaDTO>>() {}.getType());
    }

    @GetMapping("/{id}")
    public ResenaDTO getResena(@PathVariable Long id) throws EntityNotFoundException {
        ResenaEntity resena = resenaService.obtenerResena(id);
        return modelMapper.map(resena, ResenaDTO.class);
    }

    @PostMapping
    public ResenaDTO createResena(@RequestBody ResenaDTO resenaDTO) throws IllegalOperationException, EntityNotFoundException {
        ResenaEntity resenaEntity = modelMapper.map(resenaDTO, ResenaEntity.class);
        ResenaEntity nuevaResena = resenaService.crearResena(resenaEntity, resenaDTO.getCliente().getId(), resenaDTO.getTeatro().getId(), resenaDTO.getObraDeTeatro().getId());
        return modelMapper.map(nuevaResena, ResenaDTO.class);
    }

    @PutMapping("/{id}")
    public ResenaDTO updateResena(@PathVariable Long id, @RequestBody ResenaDTO resenaDTO) throws EntityNotFoundException, IllegalOperationException {
        ResenaEntity resenaEntity = modelMapper.map(resenaDTO, ResenaEntity.class);
        ResenaEntity resenaActualizada = resenaService.actualizarResena(id, resenaEntity);
        return modelMapper.map(resenaActualizada, ResenaDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteResena(@PathVariable Long id) throws EntityNotFoundException {
        resenaService.eliminarResena(id);
    }
    
}
