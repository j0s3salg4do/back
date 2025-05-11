package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ResenaRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.EventoEspecialRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeatroService {


    @Autowired
    private TeatroRepository teatroRepository;
    @Autowired
    private ResenaRepository resenaRepository;
    @Autowired
    private EventoEspecialRepository eventoEspecialRepository;

    @Transactional
    public TeatroEntity crearTeatro(TeatroEntity teatro) throws IllegalOperationException {
        log.info("Inicia proceso de creación de teatro.");

        if (teatro.getNombreTeatro() == null || teatro.getNombreTeatro().isEmpty()) {
            throw new IllegalOperationException("El nombre del teatro no puede estar vacío.");
        }

        if (teatro.getDireccionTeatro() == null || teatro.getDireccionTeatro().isEmpty()) {
            throw new IllegalOperationException("La dirección del teatro no puede estar vacía.");
        }

        if (teatro.getCapacidad() <= 0) {
            throw new IllegalOperationException("La capacidad del teatro debe ser mayor a 0.");
        }

        TeatroEntity nuevoTeatro = teatroRepository.save(teatro);
        log.info("Finaliza proceso de creación de teatro.");

        return nuevoTeatro;
    }

    @Transactional
    public TeatroEntity editarTeatro(Long teatroId, TeatroEntity teatroDetalles) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de edición de teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        
        if (teatroDetalles.getNombreTeatro() == null || teatroDetalles.getNombreTeatro().isEmpty()) {
            throw new IllegalOperationException("El nombre del teatro no puede estar vacío.");
        }
        if (teatroDetalles.getDireccionTeatro() == null || teatroDetalles.getDireccionTeatro().isEmpty()) {
            throw new IllegalOperationException("La dirección del teatro no puede estar vacía.");
        }
        if (teatroDetalles.getCapacidad() <= 0) {
            throw new IllegalOperationException("La capacidad del teatro debe ser mayor a 0.");
        }

        TeatroEntity teatroActualizado = teatro.get();
        teatroActualizado.setNombreTeatro(teatroDetalles.getNombreTeatro());
        teatroActualizado.setDireccionTeatro(teatroDetalles.getDireccionTeatro());
        teatroActualizado.setCapacidad(teatroDetalles.getCapacidad());

        TeatroEntity teatroEditado = teatroRepository.save(teatroActualizado);
        log.info("Finaliza proceso de edición de teatro.");

        return teatroEditado;
    }

    @Transactional
    public void eliminarTeatro(Long teatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        teatro.get().eliminarResenas();
        teatroRepository.deleteById(teatroId);
        log.info("Finaliza proceso de eliminación de teatro con id = {}", teatroId);
    }

    @Transactional
    public List<TeatroEntity> obtenerTeatros() throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todos los teatros.");

        List<TeatroEntity> teatros = teatroRepository.findAll();
        if (teatros.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron teatros.");
        }

        log.info("Finaliza proceso de consultar todos los teatros.");
        return teatros;
    }

    @Transactional
    public TeatroEntity buscarTeatro(Long teatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de teatro por id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        log.info("Finaliza proceso de búsqueda de teatro por id = {}", teatroId);
        return teatro.get();
    }

    @Transactional
    public TeatroEntity buscarTeatroPorNombre(String nombreTeatro) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de teatro por nombre = {}", nombreTeatro);

        TeatroEntity teatro = teatroRepository.findByNombreTeatro(nombreTeatro);
        if (teatro == null) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        log.info("Finaliza proceso de búsqueda de teatro por nombre = {}", nombreTeatro);
        return teatro;
    }

    @Transactional
    public void borrarTodos() {
        log.info("Inicia el proceso de borrar todos los teatros.");

        long total_teatros = teatroRepository.count();
        if (total_teatros == 0) {
            log.info("No se encontraron teatros para borrar.");
            return;
        }

        teatroRepository.deleteAll();
        log.info("Se borraron todos los teatros. Total eliminados: {}", total_teatros);
    }

    @Transactional
    public TeatroEntity buscarTeatroPorDireccion(String direccionTeatro) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de teatro por dirección = {}", direccionTeatro);

        TeatroEntity teatro = teatroRepository.findByDireccionTeatro(direccionTeatro);
        if (teatro == null) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        log.info("Finaliza proceso de búsqueda de teatro por dirección = {}", direccionTeatro);
        return teatro;
    }

    @Transactional
    public TeatroEntity buscarTeatroPorCapacidad(int capacidad) throws EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de teatro por capacidad = {}", capacidad);

        TeatroEntity teatro = teatroRepository.findByCapacidad(capacidad);
        if (teatro == null) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        log.info("Finaliza proceso de búsqueda de teatro por capacidad = {}", capacidad);
        return teatro;
    }

    @Transactional
    public void eliminarResenas(Long teatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de reseñas de teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        teatro.get().eliminarResenas();
        teatroRepository.save(teatro.get());
        log.info("Finaliza proceso de eliminación de reseñas de teatro con id = {}", teatroId);
    }

    @Transactional
    public void eliminarFunciones(Long teatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de funciones de teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        teatro.get().eliminarFunciones();
        teatroRepository.save(teatro.get());
        log.info("Finaliza proceso de eliminación de funciones de teatro con id = {}", teatroId);
    }
    
    @Transactional
    public void eliminarTodos() {
        log.info("Inicia proceso de eliminación de todos los teatros.");

        teatroRepository.deleteAll();
        log.info("Finaliza proceso de eliminación de todos los teatros.");
    }

    @Transactional
    public void eliminarTeatroPorNombre(String nombreTeatro) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de teatro por nombre = {}", nombreTeatro);

        TeatroEntity teatro = teatroRepository.findByNombreTeatro(nombreTeatro);
        if (teatro == null) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        teatroRepository.delete(teatro);
        log.info("Finaliza proceso de eliminación de teatro por nombre = {}", nombreTeatro);
    }

    @Transactional
    public ResenaEntity crearResenaParaTeatro(Long TeatroId, ResenaEntity resena)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación de reseña para teatro con id = {}", TeatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(TeatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        resena.setTeatro(teatro.get());
        ResenaEntity nuevaResena = resenaRepository.save(resena);
        log.info("Finaliza proceso de creación de reseña para teatro con id = {}", TeatroId);

        return nuevaResena;
    }

    @Transactional
    public List<ResenaEntity> obtenerResenasParaTeatro(Long teatroId)
            throws EntityNotFoundException {
        log.info("Inicia proceso de consultar reseñas para teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        List<ResenaEntity> resenas = teatro.get().getResenas();
        log.info("Finaliza proceso de consultar reseñas para teatro con id = {}", teatroId);

        return resenas;
    }

    @Transactional
    public List<FuncionEntity> obtenerFuncionesParaTeatro(Long teatroId)
            throws EntityNotFoundException {
        log.info("Inicia proceso de consultar funciones para teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        List<FuncionEntity> funciones = teatro.get().getFunciones();
        log.info("Finaliza proceso de consultar funciones para teatro con id = {}", teatroId);

        return funciones;
    }

    @Transactional
    public EventoEspecialEntity crearEventoEspecialParaTeatro(Long teatroId, EventoEspecialEntity eventoEspecial)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación de evento especial para teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        eventoEspecial.setTeatro(teatro.get());
        EventoEspecialEntity nuevoEventoEspecial = eventoEspecialRepository.save(eventoEspecial);
        log.info("Finaliza proceso de creación de evento especial para teatro con id = {}", teatroId);

        return nuevoEventoEspecial;
    }

    @Transactional
    public List<EventoEspecialEntity> obtenerEventosEspecialesParaTeatro(Long teatroId)
            throws EntityNotFoundException {
        log.info("Inicia proceso de consultar eventos especiales para teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        List<EventoEspecialEntity> eventosEspeciales = teatro.get().getEventosEspeciales();
        log.info("Finaliza proceso de consultar eventos especiales para teatro con id = {}", teatroId);

        return eventosEspeciales;
    }

    @Transactional
    public void eliminarEventosEspeciales(Long teatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de eliminación de eventos especiales de teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }
        teatro.get().getEventosEspeciales().clear();
        teatroRepository.save(teatro.get());
        log.info("Finaliza proceso de eliminación de eventos especiales de teatro con id = {}", teatroId);
    }

    @Transactional
    public FuncionEntity crearFuncionParaTeatro(Long teatroId, FuncionEntity funcion)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creación de función para teatro con id = {}", teatroId);

        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro no fue encontrado.");
        }

        TeatroEntity teatroo = teatro.get();
        funcion.setTeatro(teatroo);
        teatroo.getFunciones().add(funcion);
        teatroRepository.save(teatroo);

        log.info("Finaliza proceso de creación de función para teatro con id = {}", teatroId);
        return funcion;
    }

}
