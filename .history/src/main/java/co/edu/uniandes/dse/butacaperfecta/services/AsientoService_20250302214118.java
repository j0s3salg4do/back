package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.repositories.AsientoRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.BoletoRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AsientoService {

    @Autowired 
    AsientoRepository asientoRepository;

    @Autowired
    TeatroRepository teatroRepository;

    @Autowired
    BoletoRepository boletaRepository;
    
    /**
     * Obtiene la lista de asientos de un teatro específico.
     *
     * @param teatroId ID del teatro del cual se desean consultar los asientos.
     * @return Lista de entidades AsientoEntity pertenecientes al teatro.
     * @throws EntityNotFoundException Si el teatro con el ID especificado no existe.
     */

    @Transactional
    public List<AsientoEntity> getAsientos(Long teatroId) throws EntityNotFoundException {
    log.info("Inicia proceso de consultar los asientos del teatro con Id = {}", teatroId);

    TeatroEntity teatro = teatroRepository.findById(teatroId)
            .orElseThrow(() -> new EntityNotFoundException("No se encontró el teatro con Id = " + teatroId));

    Hibernate.initialize(teatro.getAsientos()); // 
    return teatro.getAsientos();
    }


    /**
     * Obtiene un asiento específico dentro de un teatro.
     *
     * @param teatroId  ID del teatro al que pertenece el asiento.
     * @param asientoId ID del asiento que se desea consultar.
     * @return La entidad AsientoEntity correspondiente al ID proporcionado.
     * @throws EntityNotFoundException Si el teatro o el asiento no existen o si el asiento no pertenece al teatro.
     */

    @Transactional
    public AsientoEntity getAsiento(Long teatroId, Long asientoId) throws EntityNotFoundException 
    {
        log.info("Inicia proceso de consultar el asiento con id = {} del teatro con id = {}", asientoId, teatroId);
        
        TeatroEntity teatro = teatroRepository.findById(teatroId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el teatro con Id = " + teatroId));
        
        AsientoEntity asiento = asientoRepository.findById(asientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el asiento con Id = " + asientoId));
        
        // Validar que el asiento pertenece al teatro
        if (!teatro.getAsientos().contains(asiento)) {
            throw new EntityNotFoundException("El asiento con Id = " + asientoId + " no pertenece al teatro con Id = " + teatroId);
        }
    
        log.info("Se encontró el asiento con id = {} en el teatro con id = {}", asientoId, teatroId);
        return asiento;
    }
    
    /**
     * Actualiza la información de un asiento dentro de un teatro.
     *
     * @param teatroId      ID del teatro al que pertenece el asiento.
     * @param asientoId     ID del asiento a actualizar.
     * @param asientoEntity Objeto AsientoEntity con los nuevos datos del asiento.
     * @return La entidad AsientoEntity actualizada.
     * @throws EntityNotFoundException Si el teatro o el asiento no existen o si el asiento no pertenece al teatro.
     */

    @Transactional
    public AsientoEntity updateAsiento(Long teatroId, Long asientoId, AsientoEntity asientoEntity) throws EntityNotFoundException {
        log.info("Inicia el proceso de actualizar asiento con id = {} en el teatro con id = {}", asientoId, teatroId);
    
        
        TeatroEntity teatro = teatroRepository.findById(teatroId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el teatro con Id = " + teatroId));
    
        
        AsientoEntity asientoExistente = asientoRepository.findById(asientoId)
                .orElseThrow(() -> new EntityNotFoundException("No se encontró el asiento con Id = " + asientoId));
    
        
        if (!teatro.getAsientos().contains(asientoExistente)) {
            throw new EntityNotFoundException("El asiento con Id = " + asientoId + " no pertenece al teatro con Id = " + teatroId);
        }
    
        asientoExistente.setDisponible(asientoEntity.isDisponible());
        asientoExistente.setNumero(asientoEntity.getNumero()); 
        asientoExistente.setUbicacion(asientoEntity.getUbicacion());
    
        log.info("Asiento con id = {} actualizado correctamente en el teatro con id = {}", asientoId, teatroId);
    
        return asientoRepository.save(asientoExistente);
    }

    /**
     * Consulta la disponibilidad de un asiento.
     *
     * @param asientoId ID del asiento a consultar.
     * @return true si el asiento está disponible, false si está ocupado.
     * @throws EntityNotFoundException Si el asiento no existe.
     */

    @Transactional
    public boolean consultarDisponibilidad(Long asientoId) throws EntityNotFoundException
    {
        log.info("Inicia el proceso de consultar la disponibilidad del asiento con Id = {}", asientoId);
        Optional<AsientoEntity> asiento = asientoRepository.findById(asientoId);
        if (asiento.isEmpty())
        {
            throw new EntityNotFoundException("No se encontró el asiento con Id = " + asientoId);
        }
        return asiento.get().isDisponible();
    }

    /**
     * Cambia el estado de disponibilidad de un asiento.
     *
     * @param asientoId      ID del asiento a modificar.
     * @param disponibilidad Nuevo estado de disponibilidad (true para disponible, false para ocupado).
     * @throws EntityNotFoundException Si el asiento no existe.
     */

    @Transactional
    public void cambiarEstadoDisponibilidad(Long asientoId, boolean disponibilidad) throws EntityNotFoundException
    {
        log.info("Inicia procceso de cambiar estado de disponibilidad del asiento con id = {}", asientoId);
        Optional<AsientoEntity> asiento = asientoRepository.findById(asientoId);
        if (asiento.isEmpty())
        {
            throw new EntityNotFoundException("No se encontró el asiento con Id = " + asientoId);
        }

        asiento.get().setDisponible(disponibilidad);
        asientoRepository.save(asiento.get());
        log.info("Se cambió el estado de disponibilidad del asiento con id = {} a {}", asientoId, disponibilidad);
    }    
}
