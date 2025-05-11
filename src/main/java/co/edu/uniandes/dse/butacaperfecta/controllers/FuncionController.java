package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDetailDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.BoletoDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.FuncionService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/funciones")
public class FuncionController {
    
    @Autowired
    private FuncionService funcionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFunciones() {
        List<FuncionEntity> funciones = funcionService.getTodasLasFunciones();
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionDetailDTO getFuncion(@PathVariable Long id) throws EntityNotFoundException {
        FuncionEntity funcion = funcionService.getFuncion(id);
        return modelMapper.map(funcion, FuncionDetailDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionDTO create(@RequestBody FuncionDTO funcion) throws IllegalOperationException {
        FuncionEntity funcionEntity = funcionService.crearFuncion(modelMapper.map(funcion, FuncionEntity.class));
        return modelMapper.map(funcionEntity, FuncionDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionDetailDTO update(@PathVariable Long id, @RequestBody FuncionDTO funcion) throws IllegalOperationException {
        FuncionEntity funcionEntity = funcionService.updateFuncion(modelMapper.map(funcion, FuncionEntity.class));
        return modelMapper.map(funcionEntity, FuncionDetailDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws IllegalOperationException {
        funcionService.eliminarFuncion(id);
    }

    @GetMapping("/fecha/{fecha}")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFuncionesPorFecha(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha) {
        List<FuncionEntity> funciones = funcionService.getFuncionesByFecha(fecha);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/hora/{hora}")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFuncionesPorHora(@PathVariable String hora) {
        List<FuncionEntity> funciones = funcionService.getFuncionesByHora(hora);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/fecha/{fecha}/hora/{hora}")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFuncionesPorFechaYHora(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @PathVariable String hora) {
        List<FuncionEntity> funciones = funcionService.getFuncionesByFechaAndHora(fecha, hora);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/obra/{ObraDeTeatro}")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFuncionesPorObra(@PathVariable Long id) {
        ObraDeTeatroEntity obra = new ObraDeTeatroEntity();
        obra.setId(id);
        List<FuncionEntity> funciones = funcionService.getFuncionesByObraDeTeatro(obra);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/fecha/{fecha}/hora/{hora}/obra/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionDetailDTO> getFuncionesPorFechaYHoraYObra(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @PathVariable String hora,
            @PathVariable Long id) {
        ObraDeTeatroEntity obra = new ObraDeTeatroEntity();
        obra.setId(id);
        List<FuncionEntity> funciones = funcionService.getFuncionesByFechaAndHoraAndObraDeTeatro(fecha, hora, obra);
        return modelMapper.map(funciones, new TypeToken<List<FuncionDetailDTO>>() {}.getType());
    }

    @GetMapping("/fecha/{fecha}/hora/{hora}/obra/{id}/teatro/{teatro}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionDetailDTO getFuncionesPorFechaYHoraYObraYTeatro(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha,
            @PathVariable String hora,
            @PathVariable Long id,
            @PathVariable Long teatro) {
        ObraDeTeatroEntity obra = new ObraDeTeatroEntity();
        obra.setId(id);
        TeatroEntity teatroEntity = new TeatroEntity();
        teatroEntity.setId(teatro);
        FuncionEntity funcion = funcionService.getFuncionByFechaAndHoraAndObraDeTeatroAndTeatro(fecha, hora, obra, teatroEntity);
        return modelMapper.map(funcion, FuncionDetailDTO.class);
    }

    @GetMapping("/{id}/boletos")
    @ResponseStatus(HttpStatus.OK)
    public List<BoletoDTO> getBoletos(@PathVariable Long id) {
        List<BoletoEntity> boletos = funcionService.getBoletosByFuncion(id);
        return modelMapper.map(boletos, new TypeToken<List<BoletoDTO>>() {}.getType());
    }

}

