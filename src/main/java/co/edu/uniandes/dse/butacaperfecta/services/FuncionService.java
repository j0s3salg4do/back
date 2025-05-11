package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.FuncionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FuncionService {
    
    @Autowired
    private FuncionRepository funcionRepository;
    
    @Transactional
    public FuncionEntity crearFuncion(FuncionEntity funcion) throws IllegalOperationException {
        log.info("Inicia proceso de creación de una función.");
        List<FuncionEntity> funciones = funcionRepository.findByFechaAndHoraAndObraDeTeatro(
            funcion.getFecha(), 
            funcion.getHora(), 
            funcion.getObraDeTeatro()
        );
        for (FuncionEntity f : funciones) {
            
            if (f.getTeatro().equals(funcion.getTeatro())) {
                    throw new IllegalOperationException("Ya existe una función con esa fecha, hora y teatro.");
                }
            
        }
        log.info("Termina proceso de creación de una función.");
        FuncionEntity nuevaFuncion= funcionRepository.save(funcion);
        return nuevaFuncion;
    }

    @Transactional
    public void eliminarFuncion(Long id) throws IllegalOperationException {
        log.info("Inicia proceso de eliminación de función.");
        Optional<FuncionEntity> funcionAEliminar = funcionRepository.findById(id);
        if (funcionAEliminar.isEmpty()) {
            throw new EntityNotFoundException("No existe una función con ese id.");
        }
        if (!funcionAEliminar.get().getBoletos().isEmpty()) {
            throw new IllegalOperationException("No se puede eliminar una función con boletos asociados.");
        }
        funcionRepository.delete(funcionAEliminar.get());
        log.info("Termina proceso de eliminación de función.");
    }
    
    @Transactional
    public FuncionEntity updateFuncion(FuncionEntity funcion) throws IllegalOperationException {
        log.info("Inicia proceso de actualización de función.");
        
        Optional<FuncionEntity> funcionAActualizar = funcionRepository.findById(funcion.getId());
        if (funcionAActualizar.isEmpty()) {
            throw new EntityNotFoundException("No existe una función con ese id.");
        }
        
        List<FuncionEntity> funciones = funcionRepository.findByFechaAndHoraAndObraDeTeatro(
            funcion.getFecha(), funcion.getHora(), funcion.getObraDeTeatro()
        );
        
        for (FuncionEntity f : funciones) {
            if (!f.getId().equals(funcion.getId()) && f.getTeatro().equals(funcion.getTeatro())) {
                throw new IllegalOperationException("Ya existe otra función con esa fecha, hora y teatro.");
            }
        }

        FuncionEntity funcionActualizada = funcionRepository.save(funcion);
        log.info("Termina proceso de actualización de función.");
        return funcionActualizada;
    }


    @Transactional
    public FuncionEntity getFuncion(Long id) {
        log.info("Inicia proceso de consultar función.");
        Optional<FuncionEntity> funcion = funcionRepository.findById(id);
        if (funcion.isEmpty()) {
            throw new EntityNotFoundException("No existe una función con ese id.");
        }
        log.info("Termina proceso de consultar función.");
        return funcion.get();
    }

    @Transactional
    public FuncionEntity getFuncionByFechaAndHoraAndObraDeTeatroAndTeatro(Date fecha, String hora, ObraDeTeatroEntity obraDeTeatro, TeatroEntity teatro) {
        log.info("Inicia proceso de consultar función por fecha, hora, obra de teatro y teatro.");
        List<FuncionEntity> funciones = funcionRepository.findByFechaAndHoraAndObraDeTeatro(fecha, hora, obraDeTeatro);
        for (FuncionEntity f : funciones) {
            if (f.getTeatro().equals(teatro)) {
                log.info("Termina proceso de consultar función por fecha, hora, obra de teatro y teatro.");
                return f;
            }
        }
        throw new EntityNotFoundException("No existe una función con esa fecha, hora, obra de teatro y teatro.");
    }

    @Transactional
    public List<FuncionEntity> getFuncionesByFecha(Date fecha) {
        log.info("Inicia proceso de consultar funciones por fecha.");
        List<FuncionEntity> funciones = funcionRepository.findByFecha(fecha);
        if (funciones.isEmpty()) {
            throw new EntityNotFoundException("No existen funciones con esa fecha.");
        }
        log.info("Termina proceso de consultar funciones por fecha.");
        return funciones;
    }

    @Transactional
    public List<FuncionEntity> getFuncionesByHora(String hora) {
        log.info("Inicia proceso de consultar funciones por hora.");
        List<FuncionEntity> funciones = funcionRepository.findByHora(hora);
        if (funciones.isEmpty()) {
            throw new EntityNotFoundException("No existen funciones con esa hora.");
        }
        log.info("Termina proceso de consultar funciones por hora.");
        return funciones;
    }

    @Transactional
    public List<FuncionEntity> getFuncionesByFechaAndHora(Date fecha, String hora) {
        log.info("Inicia proceso de consultar funciones por fecha y hora.");
        List<FuncionEntity> funciones = funcionRepository.findByFechaAndHora(fecha, hora);
        if (funciones.isEmpty()) {
            throw new EntityNotFoundException("No existen funciones con esa fecha y hora.");
        }
        log.info("Termina proceso de consultar funciones por fecha y hora.");
        return funciones;
    }

    @Transactional
    public List<FuncionEntity> getFuncionesByObraDeTeatro(ObraDeTeatroEntity obraDeTeatro) {
        log.info("Inicia proceso de consultar funciones por obra de teatro.");
        List<FuncionEntity> funciones = funcionRepository.findByObraDeTeatro(obraDeTeatro);
        if (funciones.isEmpty()) {
            throw new EntityNotFoundException("No existen funciones con esa obra de teatro.");
        }
        log.info("Termina proceso de consultar funciones por obra de teatro.");
        return funciones;
    }

    @Transactional
    public List<FuncionEntity> getFuncionesByFechaAndHoraAndObraDeTeatro(Date fecha, String hora, ObraDeTeatroEntity obraDeTeatro) {
        log.info("Inicia proceso de consultar funciones por fecha, hora y obra de teatro.");
        List<FuncionEntity> funciones = funcionRepository.findByFechaAndHoraAndObraDeTeatro(fecha, hora, obraDeTeatro);
        if (funciones.isEmpty()) {
            throw new EntityNotFoundException("No existen funciones con esa fecha, hora y obra de teatro.");
        }
        log.info("Termina proceso de consultar funciones por fecha, hora y obra de teatro.");
        return funciones;
    }

    
@Transactional
public List<BoletoEntity> getBoletosByFuncion(Long funcionId) {
    log.info("Inicia proceso de consultar boletos por función.");
    Optional<FuncionEntity> funcion = funcionRepository.findById(funcionId);
    if (funcion.isEmpty()) {
        throw new EntityNotFoundException("No existe una función con ese id.");
    }
    List<BoletoEntity> boletos = funcion.get().getBoletos();
    if (boletos.isEmpty()) {
        throw new EntityNotFoundException("No existen boletos para esa función.");
    }
    log.info("Termina proceso de consultar boletos por función.");
    return boletos;
}

    @Transactional
    public List<FuncionEntity> getTodasLasFunciones() {
        log.info("Inicia proceso de consultar todas las funciones.");
        List<FuncionEntity> funciones = funcionRepository.findAll();
        log.info("Termina proceso de consultar todas las funciones.");
        return funciones;
    }
}