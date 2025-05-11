package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.dto.AdministradorDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.ObraDeTeatroDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.TeatroDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.AdministradorEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import org.modelmapper.TypeToken;
import co.edu.uniandes.dse.butacaperfecta.services.AdministradorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/administradores")
public class AdministradorController {
    
    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AdministradorDTO> findAll() {
        List<AdministradorEntity> administradores = administradorService.getAdministradores();
        return modelMapper.map(administradores, new TypeToken<List<AdministradorDTO>>() {}.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AdministradorDTO findOne(@PathVariable Long id) throws EntityNotFoundException{
        AdministradorEntity administradorEntity = administradorService.findById(id);
        return modelMapper.map(administradorEntity, AdministradorDTO.class);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AdministradorDTO create(@RequestBody AdministradorDTO administradorDTO) throws IllegalOperationException {
        AdministradorEntity administradorEntity = administradorService.crearAdministrador(modelMapper.map(administradorDTO, AdministradorEntity.class));
        return modelMapper.map(administradorEntity, AdministradorDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AdministradorDTO update(@PathVariable Long id, @RequestBody AdministradorDTO administradorDTO) throws EntityNotFoundException {
        AdministradorEntity administradorEntity = administradorService.update(id, modelMapper.map(administradorDTO, AdministradorEntity.class));
        return modelMapper.map(administradorEntity, AdministradorDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        administradorService.delete(id);
    }

    @PutMapping("/{adminId}/teatro/{teatroId}")
    @ResponseStatus(HttpStatus.OK)
    public AdministradorDTO asignarTeatro(@PathVariable Long adminId, @PathVariable Long teatroId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorService.asignarTeatro(adminId, teatroId);
        return modelMapper.map(administrador, AdministradorDTO.class);
    }

    @GetMapping("/{adminId}/teatro")
    @ResponseStatus(HttpStatus.OK)
    public TeatroDTO obtenerTeatro(@PathVariable Long adminId) throws EntityNotFoundException {
        TeatroEntity teatro = administradorService.obtenerTeatro(adminId);
        return modelMapper.map(teatro, TeatroDTO.class);
    }

    @DeleteMapping("/{adminId}/teatro")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarTeatro(@PathVariable Long adminId) throws EntityNotFoundException {
        administradorService.eliminarTeatro(adminId);
    }

    
    @PutMapping("/{adminId}/obra/{obraId}")
    @ResponseStatus(HttpStatus.OK)
    public AdministradorDTO asignarObra(@PathVariable Long adminId, @PathVariable Long obraId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorService.asignarObra(adminId, obraId);
        return modelMapper.map(administrador, AdministradorDTO.class);
    }

    @GetMapping("/{adminId}/obra")
    @ResponseStatus(HttpStatus.OK)
    public ObraDeTeatroDTO obtenerObra(@PathVariable Long adminId) throws EntityNotFoundException {
        ObraDeTeatroEntity obra = administradorService.obtenerObra(adminId);
        return modelMapper.map(obra, ObraDeTeatroDTO.class);
    }

    @DeleteMapping("/{adminId}/obra")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarObra(@PathVariable Long adminId) throws EntityNotFoundException {
        administradorService.eliminarObra(adminId);
    }
}

    
