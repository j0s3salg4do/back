package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.services.AsientoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/teatros/{teatroId}/asientos")
public class AsientoController {
    
    @Autowired
    private AsientoService asientoService;


    @PostMapping
    public ResponseEntity<AsientoEntity> crearAsiento(@PathVariable Long teatroId, @RequestBody AsientoEntity asiento) {
        try {
            AsientoEntity nuevoAsiento = asientoService.crearAsiento(teatroId, asiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAsiento);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Obtiene todos los asientos de un teatro.
     * @param teatroId ID del teatro.
     * @return Lista de asientos.
     */
    @GetMapping
    public ResponseEntity<List<AsientoEntity>> obtenerAsientos(@PathVariable Long teatroId) {
        try {
            List<AsientoEntity> asientos = asientoService.getAsientos(teatroId);
            return ResponseEntity.ok(asientos);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene un asiento específico de un teatro.
     * @param teatroId ID del teatro.
     * @param asientoId ID del asiento.
     * @return Asiento encontrado.
     */
    @GetMapping("/{asientoId}")
    public ResponseEntity<AsientoEntity> obtenerAsiento(@PathVariable Long teatroId, @PathVariable Long asientoId) {
        try {
            AsientoEntity asiento = asientoService.getAsiento(teatroId, asientoId);
            return ResponseEntity.ok(asiento);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Actualiza un asiento en un teatro.
     * @param teatroId ID del teatro.
     * @param asientoId ID del asiento a actualizar.
     * @param asientoEntity Datos actualizados del asiento.
     * @return Asiento actualizado.
     */
    @PutMapping("/{asientoId}")
    public ResponseEntity<AsientoEntity> actualizarAsiento(@PathVariable Long teatroId, @PathVariable Long asientoId, @RequestBody AsientoEntity asientoEntity) {
        try {
            AsientoEntity actualizado = asientoService.updateAsiento(teatroId, asientoId, asientoEntity);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Consulta la disponibilidad de un asiento.
     * @param asientoId ID del asiento.
     * @return true si está disponible, false si está ocupado.
     */
    @GetMapping("/{asientoId}/disponibilidad")
    public ResponseEntity<Boolean> consultarDisponibilidad(@PathVariable Long asientoId) {
        try {
            boolean disponible = asientoService.consultarDisponibilidad(asientoId);
            return ResponseEntity.ok(disponible);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Cambia el estado de disponibilidad de un asiento.
     * @param asientoId ID del asiento.
     * @param disponibilidad Nuevo estado de disponibilidad.
     * @return Respuesta con estado actualizado.
     */
    @PutMapping("/{asientoId}/disponibilidad")
    public ResponseEntity<Void> cambiarDisponibilidad(@PathVariable Long asientoId, @RequestParam boolean disponibilidad) {
        try {
            asientoService.cambiarEstadoDisponibilidad(asientoId, disponibilidad);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
