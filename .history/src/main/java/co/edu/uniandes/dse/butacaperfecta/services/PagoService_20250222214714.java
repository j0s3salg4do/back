package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.PagoRepository;


@Slf4j
@Service
public class PagoService {

    @Autowired
    PagoRepository PagoRepository;
    
    private void validarMetodoPago(String metodoPago) throws IllegalOperationException
    {
        List<String> metodosValidos = List.of("Tarjeta de Crédito", "Tarjeta de Débito", "PSE", "Efectivo");
        if (!metodosValidos.contains(metodoPago))
        {
            throw new IllegalOperationException("El método de pago no es válido");
        }
    }

    public void procesarPago(String metodoPago) throws IllegalOperationException
    {
        validarMetodoPago(metodoPago);
        // Lógica adicional para procesar el pago
        log.info("Procesando pago con método: " + metodoPago);
    }



}
