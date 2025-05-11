package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.butacaperfecta.dto.EventoEspecialDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.ResenaDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.TeatroDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.services.TeatroService;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/teatros")
public class TeatroController {

    @Autowired
    private TeatroService teatroService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<TeatroDTO> getTeatros() throws EntityNotFoundException {
        List<TeatroEntity> teatros = teatroService.obtenerTeatros();
        return modelMapper.map(teatros, new TypeToken<List<TeatroDTO>>() {}.getType());
    }

    @GetMapping("/{id}")
    public TeatroDTO getTeatro(@PathVariable Long id) throws EntityNotFoundException {
        TeatroEntity teatro = teatroService.buscarTeatro(id);
        return modelMapper.map(teatro, TeatroDTO.class);
    }

    @PostMapping
    public TeatroDTO createTeatro(@RequestBody TeatroDTO teatroDTO) throws IllegalOperationException{
        TeatroEntity teatroEntity = modelMapper.map(teatroDTO, TeatroEntity.class);
        TeatroEntity nuevoTeatro = teatroService.crearTeatro(teatroEntity);
        return modelMapper.map(nuevoTeatro, TeatroDTO.class);
    }

    @PutMapping("/{id}")
    public TeatroDTO updateTeatro(@PathVariable Long id, @RequestBody TeatroDTO teatroDTO) throws EntityNotFoundException, IllegalOperationException {
        TeatroEntity teatroEntity = modelMapper.map(teatroDTO, TeatroEntity.class);
        TeatroEntity teatroActualizado = teatroService.editarTeatro(id, teatroEntity);
        return modelMapper.map(teatroActualizado, TeatroDTO.class);
    }

    @DeleteMapping("/{id}")
    public void deleteTeatro(@PathVariable Long id) throws EntityNotFoundException {
        teatroService.eliminarTeatro(id);
    }

    @PostMapping("/{id}/resenas")
    public TeatroDTO createResenaForTeatro(@PathVariable Long id, @RequestBody ResenaDTO resenaDTO)
            throws EntityNotFoundException, IllegalOperationException {
        ResenaEntity resenaEntity = modelMapper.map(resenaDTO, ResenaEntity.class);
        ResenaEntity nuevaResena = teatroService.crearResenaParaTeatro(id, resenaEntity);
        
        return modelMapper.map(nuevaResena.getTeatro(), TeatroDTO.class);
    }

    @GetMapping("/{id}/resenas")
    public List<ResenaDTO> getResenasForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        List<ResenaEntity> resenas = teatroService.obtenerResenasParaTeatro(id);
        return modelMapper.map(resenas, new TypeToken<List<ResenaDTO>>() {}.getType());
    }

    @DeleteMapping("/{id}/resenas")
    public void deleteResenasForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        teatroService.eliminarResenas(id);
    }

    @GetMapping("/{id}/funciones")
    public List<FuncionDTO> getFuncionesForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        List<FuncionEntity> funciones = teatroService.obtenerFuncionesParaTeatro(id);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDTO>>() {}.getType());
    }

    @GetMapping("nombre/{nombreTeatro}")  
    public TeatroDTO getTeatroPorNombre(@PathVariable String nombreTeatro) throws EntityNotFoundException {
        TeatroEntity teatro = teatroService.buscarTeatroPorNombre(nombreTeatro);
        return modelMapper.map(teatro, TeatroDTO.class);
    }

    @PostMapping("/{id}/funciones")
    public TeatroDTO createFuncionForTeatro(@PathVariable Long id, @RequestBody FuncionDTO funcionDTO)
            throws EntityNotFoundException, IllegalOperationException {
        FuncionEntity funcionEntity = modelMapper.map(funcionDTO, FuncionEntity.class);
        FuncionEntity nuevaFuncion = teatroService.crearFuncionParaTeatro(id, funcionEntity);
        
        return modelMapper.map(nuevaFuncion.getTeatro(), TeatroDTO.class);
    }


    @GetMapping("direccion/{direccionTeatro}")
    public TeatroDTO getTeatroPorDireccion(@PathVariable String direccionTeatro) throws EntityNotFoundException {
        TeatroEntity teatro = teatroService.buscarTeatroPorDireccion(direccionTeatro);
        return modelMapper.map(teatro, TeatroDTO.class);
    }

    @DeleteMapping("/{id}/funciones")
    public void deleteFuncionesForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        teatroService.eliminarFunciones(id);
}

    @GetMapping("capacidad/{capacidad}")
    public TeatroDTO getTeatrosPorCapacidad(@PathVariable int capacidad) throws EntityNotFoundException {
        TeatroEntity teatros = teatroService.buscarTeatroPorCapacidad(capacidad);
        return modelMapper.map(teatros, TeatroDTO.class);
    }

    @DeleteMapping
    public void deleteTeatros() throws EntityNotFoundException {
        teatroService.borrarTodos();
    }

    @PostMapping("/{id}/eventosEspeciales")
    public TeatroDTO createEventoForTeatro(@PathVariable Long id, @RequestBody EventoEspecialDTO eventoDTO)
            throws EntityNotFoundException, IllegalOperationException {
        EventoEspecialEntity eventoEntity = modelMapper.map(eventoDTO, EventoEspecialEntity.class);
        EventoEspecialEntity nuevoEvento = teatroService.crearEventoEspecialParaTeatro(id, eventoEntity);
        
        return modelMapper.map(nuevoEvento.getTeatro(), TeatroDTO.class);
    }

    @GetMapping("/{id}/eventosEspeciales")
    public List<EventoEspecialDTO> getEventosForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        List<EventoEspecialEntity> eventos = teatroService.obtenerEventosEspecialesParaTeatro(id);
        return modelMapper.map(eventos, new TypeToken<List<EventoEspecialDTO>>() {}.getType());
    }

    @DeleteMapping("/{id}/eventosEspeciales")
    public void deleteEventosForTeatro(@PathVariable Long id) throws EntityNotFoundException {
        teatroService.eliminarEventosEspeciales(id);
    }
}
