package co.edu.uniandes.dse.butacaperfecta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.AsientoRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.BoletoRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoletoService {
    
    @Autowired
    private BoletoRepository boletoRepository;
    
    @Transactional
    public void eliminarBoleto(Long id) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de boleto con ID: {}", id);
        
        Optional<BoletoEntity> boletoAEliminar = boletoRepository.findById(id);
        
        if (boletoAEliminar.isEmpty()) {
            throw new EntityNotFoundException("No existe un boleto con ese ID.");
        }
        
        boletoRepository.delete(boletoAEliminar.get());
        
        log.info("Termina proceso de eliminación de boleto.");
    }

    @Transactional
    public BoletoEntity actualizarBoleto(BoletoEntity boleto) throws EntityNotFoundException {
        log.info("Inicia proceso de actualización de boleto con ID: {}", boleto.getId());
        
        if (boleto.getId() == null) {
            throw new IllegalArgumentException("El ID del boleto no puede ser nulo.");
        }

        Optional<BoletoEntity> boletoAActualizar = boletoRepository.findById(boleto.getId());
        
        if (boletoAActualizar.isEmpty()) {
            throw new EntityNotFoundException("No existe un boleto con ese ID.");
        }
        
        BoletoEntity boletoActualizado = boletoRepository.save(boleto);
        
        log.info("Termina proceso de actualización de boleto.");
        
        return boletoActualizado;
    }

    @Transactional
    public BoletoEntity crearBoleto(BoletoEntity boleto) throws IllegalOperationException {
        log.info("Inicia proceso de creación de boleto.");
        if (boleto.getFuncion() == null || boleto.getCliente() == null || boleto.getSilla() == null) {
            throw new IllegalArgumentException("El boleto debe tener una función, un cliente y una silla.");
        }
        if (boleto.getPrecio() == null || boleto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio del boleto no puede ser nulo o negativo.");
        }
        if (!boletoRepository.findByFuncionIdAndClienteIdAndSillaId(
            boleto.getFuncion().getId(), boleto.getCliente().getId(), boleto.getSilla().getId()).isEmpty()) {
            throw new IllegalOperationException("Ya existe un boleto para esa función, cliente y silla.");
        }
        BoletoEntity nuevoBoleto = boletoRepository.save(boleto);
        
        log.info("Termina proceso de creación de boleto.");
        
        return nuevoBoleto;
    }

        @Transactional
    public BoletoEntity buscarBoleto(Long id) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de boleto con ID: {}", id);
        
        Optional<BoletoEntity> boletoBuscado = boletoRepository.findById(id);
        
        if (boletoBuscado.isEmpty()) {
            throw new EntityNotFoundException("No existe un boleto con ese ID.");
        }
        
        log.info("Termina proceso de búsqueda de boleto.");
        
        return boletoBuscado.get();
    }

    @Autowired
    private AsientoRepository asientoRepository;

    @Transactional
    public void asignarSilla(Long idBoleto, Long idSilla) throws EntityNotFoundException {
        log.info("Inicia proceso de asignación de silla al boleto con ID: {}", idBoleto);
        Optional<BoletoEntity> boleto = boletoRepository.findById(idBoleto);
        if (boleto.isEmpty()) {
            throw new EntityNotFoundException("No existe un boleto con ese ID.");
        }
        Optional<AsientoEntity> asiento = asientoRepository.findById(idSilla);
        if (asiento.isEmpty()) {
            throw new EntityNotFoundException("No existe una silla con ese ID.");
        }
        boleto.get().setSilla(asiento.get());
        boletoRepository.save(boleto.get());
        log.info("Termina proceso de asignación de silla al boleto.");
    }

    @Transactional
    public List<BoletoEntity> obtenerTodosLosBoletos() {
        log.info("Inicia proceso de obtención de todos los boletos.");
        return boletoRepository.findAll();
    }

    @Transactional
    public FuncionEntity obtenerFuncionDeBoleto(Long idBoleto) throws EntityNotFoundException {
        log.info("Inicia proceso de obtención de función del boleto con ID: {}", idBoleto);
        return buscarBoleto(idBoleto).getFuncion();
    }

    @Transactional
    public AsientoEntity obtenerAsientoDeBoleto(Long idBoleto) throws EntityNotFoundException {
        log.info("Inicia proceso de obtención de asiento del boleto con ID: {}", idBoleto);
        return buscarBoleto(idBoleto).getSilla();
    }

}
