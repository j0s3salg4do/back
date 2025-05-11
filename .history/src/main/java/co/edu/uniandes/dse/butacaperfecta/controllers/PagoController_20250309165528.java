package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import co.edu.uniandes.dse.butacaperfecta.entities.PagoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.PagoService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/pagos")
public class PagoController 
{
    
    @Autowired
    private PagoService pagoService;

    /**
     * Valida si un método de pago es permitido.
     * @param metodoPago Método de pago a validar.
     * @return Respuesta con mensaje de validación.
     */
    @GetMapping("/{metodoPago}")
    public ResponseEntity<String> validarMetodoPago(@PathVariable String metodoPago) {
        try {
            pagoService.procesarPago(metodoPago);
            return ResponseEntity.ok("Método de pago válido: " + metodoPago);
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Registra un pago en un teatro específico.
     * @param idTeatro ID del teatro.
     * @param pagoEntity Datos del pago.
     * @return Pago registrado.
     */
    @PostMapping("/{idTeatro}")
    public ResponseEntity<PagoEntity> registrarPago(@PathVariable Long idTeatro, @RequestBody PagoEntity pagoEntity) {
        try {
            PagoEntity pago = pagoService.registrarPago(pagoEntity);
            return ResponseEntity.ok(pago);
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Verifica si un pago fue exitoso.
     * @param idPago ID del pago.
     * @return true si fue exitoso, false si no.
     */
    @GetMapping("/{idPago}")
    public ResponseEntity<Boolean> verificarPagoExitoso(@PathVariable Long idPago) {
        try {
            boolean exitoso = pagoService.verificarPagoExitoso(idPago);
            return ResponseEntity.ok(exitoso);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtiene todos los pagos de un teatro.
     * @param idTeatro ID del teatro.
     * @return Lista de pagos.
     */
    @GetMapping("/{idTeatro}")
    public ResponseEntity<List<PagoEntity>> obtenerPagosPorTeatro(@PathVariable Long idTeatro) {
        log.info("Obteniendo pagos del teatro con ID: {}", idTeatro);
        // Implementación ficticia para simular la obtención de pagos
        return ResponseEntity.ok(List.of());
    }
}

