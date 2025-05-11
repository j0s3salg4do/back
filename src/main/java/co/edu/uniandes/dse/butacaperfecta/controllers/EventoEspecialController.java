package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.butacaperfecta.dto.BoletoDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.EventoEspecialDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.EventoEspecialDetailDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.EventoEspecialService;

@RestController
@RequestMapping("/eventosEspeciales")
public class EventoEspecialController {

    @Autowired
    private EventoEspecialService eventoEspecialService;

    @Autowired
    private ModelMapper modelMapper;

    /** 1. Obtener todos los eventos especiales */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoEspecialDetailDTO> findAll() throws EntityNotFoundException {
        
        List<EventoEspecialEntity> eventos = eventoEspecialService.obtenerTodos();
        List<EventoEspecialDetailDTO> eventosDTO = modelMapper.map(eventos, new TypeToken<List<EventoEspecialDetailDTO>>() {}.getType());
        
        return eventosDTO;
    }

    /** 2. Obtener un evento especial por ID */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoEspecialDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException {
        
        EventoEspecialEntity evento = eventoEspecialService.buscarEventoEspecial(id);
        EventoEspecialDetailDTO eventoDTO = modelMapper.map(evento, EventoEspecialDetailDTO.class);
        
        return eventoDTO;
    }

    /** 3. Crear un evento especial */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventoEspecialDTO create(@RequestBody EventoEspecialDTO eventoDTO) throws IllegalOperationException {
        
        EventoEspecialEntity eventoEntity = modelMapper.map(eventoDTO, EventoEspecialEntity.class);
        EventoEspecialEntity nuevoEvento = eventoEspecialService.crearEventoEspecial(eventoEntity);
        EventoEspecialDTO nuevoEventoDTO = modelMapper.map(nuevoEvento, EventoEspecialDTO.class);
        
        return nuevoEventoDTO;
    }

    /** 4. Actualizar un evento especial */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoEspecialDTO update(@PathVariable Long id, @RequestBody EventoEspecialDTO eventoDTO) throws EntityNotFoundException, IllegalOperationException {
        
        EventoEspecialEntity eventoEntity = modelMapper.map(eventoDTO, EventoEspecialEntity.class);
        EventoEspecialEntity eventoActualizado = eventoEspecialService.editarEventoEspecial(id, eventoEntity);
        EventoEspecialDTO eventoActualizadoDTO = modelMapper.map(eventoActualizado, EventoEspecialDTO.class);
        
        return eventoActualizadoDTO;
    }

    /** 5. Eliminar un evento especial */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        
        eventoEspecialService.eliminarEventoEspecial(id);
    }

    /** 6. Buscar eventos especiales por título */
    @GetMapping(params = "nombre")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoEspecialDTO> findByTitulo(@RequestParam String nombre) throws EntityNotFoundException {
        
        List<EventoEspecialEntity> eventos = eventoEspecialService.buscarPorTitulo(nombre);
        List<EventoEspecialDTO> eventosDTO = modelMapper.map(eventos, new TypeToken<List<EventoEspecialDTO>>() {}.getType());
        
        return eventosDTO;
    }

    /** 7. Buscar eventos especiales por fecha */
    @GetMapping(params = "fecha")
    @ResponseStatus(code = HttpStatus.OK)
    public List<EventoEspecialDTO> findByFecha(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha) throws EntityNotFoundException {
        
        List<EventoEspecialEntity> eventos = eventoEspecialService.buscarEventoPorFecha(fecha);
        List<EventoEspecialDTO> eventosDTO = modelMapper.map(eventos, new TypeToken<List<EventoEspecialDTO>>() {}.getType());
        
        return eventosDTO;
    }

    /** 8. Asociar un teatro a un evento especial */
    @PostMapping("/{eventoId}/teatro/{teatroId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoEspecialDTO asociarTeatro(@PathVariable Long eventoId, @PathVariable Long teatroId) throws EntityNotFoundException {
        
        EventoEspecialEntity evento = eventoEspecialService.asociarTeatro(eventoId, teatroId);
        EventoEspecialDTO eventoDTO = modelMapper.map(evento, EventoEspecialDTO.class);
        
        return eventoDTO;
    }

    /** 9. Desasociar un teatro de un evento especial */
    @DeleteMapping("/{eventoId}/teatro")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void desasociarTeatro(@PathVariable Long eventoId) throws EntityNotFoundException {
        
        eventoEspecialService.desasociarTeatro(eventoId);
    }

    /** 10. Agregar un boleto a un evento especial */
    @PostMapping("/{eventoId}/boletos/{boletoId}")
    @ResponseStatus(code = HttpStatus.OK)
    public EventoEspecialDTO agregarBoleto(@PathVariable Long eventoId, @PathVariable Long boletoId) throws EntityNotFoundException, IllegalOperationException {
        
        EventoEspecialEntity evento = eventoEspecialService.agregarBoleto(eventoId, boletoId);
        EventoEspecialDTO eventoDTO = modelMapper.map(evento, EventoEspecialDTO.class);
        
        return eventoDTO;
    }

    /** 11. Eliminar un boleto de un evento especial */
    @DeleteMapping("/{eventoId}/boletos/{boletoId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarBoleto(@PathVariable Long eventoId, @PathVariable Long boletoId) throws EntityNotFoundException, IllegalOperationException {
        
        eventoEspecialService.eliminarBoleto(eventoId, boletoId);
    }

    /** 12. Obtener todos los boletos de un evento especial */
    @GetMapping("/{eventoId}/boletos")
    @ResponseStatus(code = HttpStatus.OK)
    public List<BoletoDTO> obtenerBoletos(@PathVariable Long eventoId) throws EntityNotFoundException {
        
        List<BoletoEntity> boletos = eventoEspecialService.obtenerBoletos(eventoId);
        List<BoletoDTO> boletosDTO = modelMapper.map(boletos, new TypeToken<List<BoletoDTO>>() {}.getType());
        
        return boletosDTO;
    }

    /** 13. Eliminar eventos especiales vencidos */
    @DeleteMapping("/vencidos")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void borrarEventosVencidos(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fecha) throws EntityNotFoundException, IllegalOperationException {
        
        eventoEspecialService.borrarEventoVencido(fecha);
    }
}
