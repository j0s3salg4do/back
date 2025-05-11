package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.BoletoRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.EventoEspecialRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EventoEspecialService {

    @Autowired
    private EventoEspecialRepository eventoEspecialRepository;
    @Autowired
    private TeatroRepository teatroRepository;
    @Autowired
    private BoletoRepository boletoRepository;

    @Transactional

    public EventoEspecialEntity crearEventoEspecial(EventoEspecialEntity eventoEspecial)
            throws IllegalOperationException {
        log.info("Inicia proceso de creación de evento especial.");

        if (eventoEspecial.getNombreEvento() == null || eventoEspecial.getNombreEvento().isEmpty()) {
            throw new IllegalOperationException("El nombre del evento no puede estar vacío.");
        }

        if (eventoEspecial.getDescripcionEvento() == null || eventoEspecial.getDescripcionEvento().isEmpty()) {
            throw new IllegalOperationException("La descripción del evento no puede estar vacía.");
        }

        if (eventoEspecial.getFecha() == null) {
            throw new IllegalOperationException("La fecha del evento no puede ser nula.");
        }

        if (!eventoEspecialRepository
                .findByNombreEventoAndFecha(eventoEspecial.getNombreEvento(), eventoEspecial.getFecha()).isEmpty()) {
            throw new IllegalOperationException("Ya existe un evento especial con este nombre y fecha.");
        }

        EventoEspecialEntity nuevoEvento = eventoEspecialRepository.save(eventoEspecial);
        log.info("Finaliza proceso de creación de evento especial.");
        return nuevoEvento;
    }

    @Transactional
    public EventoEspecialEntity editarEventoEspecial(Long eventoEspecialId, EventoEspecialEntity eventoDetalles)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de edición de evento especial con id = {}", eventoEspecialId);

        Optional<EventoEspecialEntity> evento = eventoEspecialRepository.findById(eventoEspecialId);
        if (evento.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        if (eventoDetalles.getNombreEvento() == null || eventoDetalles.getNombreEvento().isEmpty()) {
            throw new IllegalOperationException("El nombre del evento no puede estar vacío.");
        }
        if (eventoDetalles.getFecha() == null) {
            throw new IllegalOperationException("La fecha del evento no puede ser nula.");
        }
        if (eventoDetalles.getDescripcionEvento() == null || eventoDetalles.getDescripcionEvento().isEmpty()) {
            throw new IllegalOperationException("La descripción del evento no puede estar vacía.");
        }
        List<EventoEspecialEntity> duplicados = eventoEspecialRepository
                .findByNombreEventoAndFecha(eventoDetalles.getNombreEvento(), eventoDetalles.getFecha());

        if (!duplicados.isEmpty() && !duplicados.get(0).getId().equals(eventoEspecialId)) {
            throw new IllegalOperationException("Ya existe otro evento con el mismo nombre y fecha.");
        }
        EventoEspecialEntity eventoActualizado = evento.get();
        eventoActualizado.setNombreEvento(eventoDetalles.getNombreEvento());
        eventoActualizado.setDescripcionEvento(eventoDetalles.getDescripcionEvento());
        eventoActualizado.setFecha(eventoDetalles.getFecha());

        log.info("Finaliza proceso de edición de evento especial con id = {}", eventoEspecialId);
        return eventoEspecialRepository.save(eventoActualizado);
    }

    @Transactional
    public void eliminarEventoEspecial(Long eventoEspecialID)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminación de evento especial por id= {}", eventoEspecialID);

        Optional<EventoEspecialEntity> evento = eventoEspecialRepository.findById(eventoEspecialID);
        if (evento.isEmpty()) {
            throw new EntityNotFoundException("El evento no fue encontrado.");
        }

        EventoEspecialEntity eventoEntity = evento.get();

        if (!eventoEntity.getBoletos().isEmpty()) {
            throw new IllegalOperationException(
                    "No se puede eliminar el evento especial porque tiene boletos asociados.");
        }

        eventoEspecialRepository.deleteById(eventoEspecialID);
        log.info("Finaliza el proceso de eliminación del evento especial con id: {}", eventoEspecialID);
    }

    @Transactional
    public EventoEspecialEntity buscarEventoEspecial(Long eventoEspecialID) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de evento especial por id= {}", eventoEspecialID);
        Optional<EventoEspecialEntity> evento = eventoEspecialRepository.findById(eventoEspecialID);
        if (evento.isEmpty()) {
            throw new EntityNotFoundException("El evento no fue encontrado.");
        }
        log.info("Finaliza el proceso de búsqueda del evento especial con id: {}", eventoEspecialID);
        return evento.get();
    }

    @Transactional
    public List<EventoEspecialEntity> obtenerTodos() throws EntityNotFoundException {
        log.info("Inicia proceso de obtener todos los eventos especiales.");
        List<EventoEspecialEntity> eventosEspeciales = eventoEspecialRepository.findAll();
        if (eventosEspeciales.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron eventos especiales.");
        }
        log.info("Finaliza el proceso de obtención de los eventos especiales");
        return eventosEspeciales;
    }

    @Transactional
    public void borrarTodos() {
        log.info("Inicia el proceso de borrar todos los eventos especiales");

        long total = eventoEspecialRepository.count();
        if (total == 0) {
            log.info("No se encontraron eventos especiales para borrar.");
            return;
        }

        eventoEspecialRepository.deleteAll();
        log.info("Se borraron todos los eventos especiales. Total eliminados: {}", total);
    }

    @Transactional
    public List<EventoEspecialEntity> buscarEventoPorFecha(Date fecha) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de eventos especiales por fecha");
        List<EventoEspecialEntity> eventosFecha = eventoEspecialRepository.findByFecha(fecha);
        if (eventosFecha.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron eventos especiales para la fecha: " + fecha);
        }
        log.info("Finaliza el proceso de obtención de los eventos especiales por fecha");
        return eventosFecha;
    }

    @Transactional
    public void borrarEventoVencido(Date fecha) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminación de eventos especiales vencidos anteriores a la fecha: {}", fecha);

        if (fecha == null) {
            throw new IllegalOperationException("La fecha no puede ser nula.");
        }

        if (fecha.after(new Date())) {
            throw new IllegalOperationException("No se pueden borrar eventos futuros.");
        }

        List<EventoEspecialEntity> eventosVencidos = eventoEspecialRepository.findByFechaLessThan(fecha);

        if (eventosVencidos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron eventos vencidos para la fecha: " + fecha);
        }

        eventoEspecialRepository.deleteAll(eventosVencidos);
        log.info("Se eliminaron {} eventos vencidos.", eventosVencidos.size());

        log.info("Finaliza proceso de eliminación de eventos especiales vencidos.");
    }

    @Transactional
    public EventoEspecialEntity asociarTeatro(Long eventoId, Long teatroId) throws EntityNotFoundException {
        Optional<EventoEspecialEntity> eventoOptional = eventoEspecialRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        Optional<TeatroEntity> teatroOptional = teatroRepository.findById(teatroId);
        if (teatroOptional.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        EventoEspecialEntity evento = eventoOptional.get();
        TeatroEntity teatro = teatroOptional.get();
        evento.setTeatro(teatro);

        return eventoEspecialRepository.save(evento);
    }

    @Transactional
    public void desasociarTeatro(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEspecialEntity> eventoOptional = eventoEspecialRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        EventoEspecialEntity evento = eventoOptional.get();
        evento.setTeatro(null);

        eventoEspecialRepository.save(evento);
    }

    @Transactional
    public EventoEspecialEntity agregarBoleto(Long eventoId, Long boletoId)
            throws EntityNotFoundException, IllegalOperationException {
        Optional<EventoEspecialEntity> eventoOptional = eventoEspecialRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        Optional<BoletoEntity> boletoOptional = boletoRepository.findById(boletoId);
        if (boletoOptional.isEmpty()) {
            throw new EntityNotFoundException("El boleto no fue encontrado.");
        }

        EventoEspecialEntity evento = eventoOptional.get();
        BoletoEntity boleto = boletoOptional.get();

        if (evento.getBoletos().contains(boleto)) {
            throw new IllegalOperationException("El boleto ya está asociado a este evento especial.");
        }

        evento.getBoletos().add(boleto);
        boleto.setEventoEspecial(evento);

        return eventoEspecialRepository.save(evento);
    }

    @Transactional
    public void eliminarBoleto(Long eventoId, Long boletoId) throws EntityNotFoundException, IllegalOperationException {
        Optional<EventoEspecialEntity> eventoOptional = eventoEspecialRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        Optional<BoletoEntity> boletoOptional = boletoRepository.findById(boletoId);
        if (boletoOptional.isEmpty()) {
            throw new EntityNotFoundException("El boleto no fue encontrado.");
        }

        EventoEspecialEntity evento = eventoOptional.get();
        BoletoEntity boleto = boletoOptional.get();

        if (!evento.getBoletos().contains(boleto)) {
            throw new IllegalOperationException("El boleto no está asociado a este evento especial.");
        }

        evento.getBoletos().remove(boleto);
        boletoRepository.delete(boleto);
    }

    @Transactional
    public List<BoletoEntity> obtenerBoletos(Long eventoId) throws EntityNotFoundException {
        Optional<EventoEspecialEntity> eventoOptional = eventoEspecialRepository.findById(eventoId);
        if (eventoOptional.isEmpty()) {
            throw new EntityNotFoundException("El evento especial no fue encontrado.");
        }

        return eventoOptional.get().getBoletos();
    }

    @Transactional
    public List<EventoEspecialEntity> buscarPorTitulo(String titulo) throws EntityNotFoundException {
        List<EventoEspecialEntity> eventos = eventoEspecialRepository.findByNombreEvento(titulo);
        if (eventos.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron eventos con el título: " + titulo);
        }
        return eventos;
    }

}
