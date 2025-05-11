package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import co.edu.uniandes.dse.butacaperfecta.entities.PagoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.PagoRepository;
import jakarta.transaction.Transactional;


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
        log.info("Procesando pago con método de pago: " + metodoPago);
    }

    @Transactional
    public PagoEntity registrarPago(PagoEntity pagoEntity) throws IllegalOperationException
    {
        log.info("Inicia proceso de registro de pago");
        validarMetodoPago(pagoEntity.getMetodoPago());
        log.info("Termina proceso de registro de pago");
        return PagoRepository.save(pagoEntity);
    }

    @Transactional
    public boolean verificarPagoExitoso(Long idPago) throws EntityNotFoundException
    {
        log.info("Inicia proceso de verificación de pago con id = {}", idPago);
        Optional<PagoEntity> pago = PagoRepository.findById(idPago);
        if (pago.isEmpty())
        {
            throw new EntityNotFoundException("No se encontró un pago con el id " + idPago);
        }
        log.info("Termina proceso de verificación de pago con id = {}", idPago);
        return pago.get().getMonto() > 0;
    }
    {
    }
}
