package co.edu.uniandes.dse.butacaperfecta.controllers;

import co.edu.uniandes.dse.butacaperfecta.entities.PagoEntity;
import co.edu.uniandes.dse.butacaperfecta.services.PagoService;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagos")
@Slf4j
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * Procesa un pago verificando si el método es válido.
     */
    @PostMapping("/procesar")
    public ResponseEntity<String> procesarPago(@RequestParam String metodoPago) {
        try {
            pagoService.procesarPago(metodoPago);
            return ResponseEntity.ok("Pago procesado con éxito usando: " + metodoPago);
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Registra un nuevo pago en la base de datos.
     */
    @PostMapping
    public ResponseEntity<?> registrarPago(@RequestBody PagoEntity pagoEntity) {
        try {
            PagoEntity nuevoPago = pagoService.registrarPago(pagoEntity);
            return ResponseEntity.ok(nuevoPago);
        } catch (IllegalOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Verifica si un pago fue exitoso.
     */
    @GetMapping("/{id}/exitoso")
    public ResponseEntity<?> verificarPagoExitoso(@PathVariable Long id) {
        try {
            boolean exitoso = pagoService.verificarPagoExitoso(id);
            return ResponseEntity.ok(exitoso);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



