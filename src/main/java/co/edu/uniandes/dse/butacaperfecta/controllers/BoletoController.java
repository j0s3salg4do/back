package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.butacaperfecta.dto.AsientoDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.BoletoDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.BoletoService;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<BoletoDTO> findAll() {
        return boletoService.obtenerTodosLosBoletos().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BoletoDTO findById(@PathVariable Long id) throws EntityNotFoundException {
        BoletoEntity boleto = boletoService.buscarBoleto(id);
        return convertToDTO(boleto);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BoletoDTO create(@RequestBody BoletoDTO dto) throws IllegalOperationException, EntityNotFoundException {
        BoletoEntity entity = convertToEntity(dto);
        BoletoEntity creado = boletoService.crearBoleto(entity);
        return convertToDTO(creado);
    }

    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public BoletoDTO update(@PathVariable Long id, @RequestBody BoletoDTO dto)
            throws EntityNotFoundException, IllegalOperationException {
        BoletoEntity entity = convertToEntity(dto);
        entity.setId(id);
        BoletoEntity actualizado = boletoService.actualizarBoleto(entity);
        return convertToDTO(actualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException {
        boletoService.eliminarBoleto(id);
    }

    @GetMapping("/{id}/silla")
    @ResponseStatus(code = HttpStatus.OK)
    public AsientoDTO getSilla(@PathVariable Long id) throws EntityNotFoundException {
        AsientoEntity asiento = boletoService.obtenerAsientoDeBoleto(id);
        AsientoDTO dto = new AsientoDTO();
        dto.setId(asiento.getId());
        dto.setFila(asiento.getFila());
        dto.setNumero(asiento.getNumero());
        return dto;
    }

    @GetMapping("/{id}/funcion")
    @ResponseStatus(code = HttpStatus.OK)
    public FuncionDTO getFuncion(@PathVariable Long id) throws EntityNotFoundException {
        FuncionEntity funcion = boletoService.obtenerFuncionDeBoleto(id);
        FuncionDTO dto = new FuncionDTO();
        dto.setId(funcion.getId());
        dto.setFecha(funcion.getFecha());
        dto.setHora(funcion.getHora());
        return dto;
    }

    private BoletoDTO convertToDTO(BoletoEntity entity) {
        BoletoDTO dto = new BoletoDTO();
        dto.setId(entity.getId());
        dto.setPrecio(entity.getPrecio());

        if (entity.getFuncion() != null) {
            dto.setFecha(entity.getFuncion().getFecha());
            dto.setTituloObra(entity.getFuncion().getObraDeTeatro().getTitulo());
            dto.setNombreTeatro(entity.getFuncion().getTeatro().getNombreTeatro());
        }

        if (entity.getCliente() != null) {
            dto.setCliente(entity.getCliente().getId().toString());
        }

        if (entity.getSilla() != null) {
            dto.setSilla(entity.getSilla().getId().toString());
            dto.setFilaSilla(entity.getSilla().getFila());
            dto.setNumeroSilla(entity.getSilla().getNumero());
        }

        return dto;
    }

    private BoletoEntity convertToEntity(BoletoDTO dto) {
        BoletoEntity entity = new BoletoEntity();
        entity.setId(dto.getId());
        entity.setPrecio(dto.getPrecio());
        return entity;
    }
}
