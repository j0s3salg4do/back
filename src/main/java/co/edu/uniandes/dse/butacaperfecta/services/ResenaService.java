package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.ClienteRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ObraDeTeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ResenaRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;
    
    @Autowired
    private TeatroRepository teatroRepository;
    
    @Autowired
    private ObraDeTeatroRepository obraRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ResenaEntity crearResena(ResenaEntity resena, long clienteId, Long teatroId, Long obraId) throws IllegalOperationException, EntityNotFoundException {
        if (resena.getComentario() == null || resena.getComentario().isEmpty()) {
            throw new IllegalOperationException("El comentario de la reseña no puede estar vacío.");
        }

        if (resena.getCalificacion() == null || resena.getCalificacion() < 1 || resena.getCalificacion() > 5) {
            throw new IllegalOperationException("La calificación de la reseña debe estar entre 1 y 5.");
        }

        Optional<ClienteEntity> cliente = clienteRepository.findById(clienteId);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("El cliente especificado no existe.");
        }
        resena.setCliente(cliente.get());
        
        if (teatroId != null) {
            Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
            if (teatro.isEmpty()) {
                throw new EntityNotFoundException("El teatro especificado no existe.");
            }
            resena.setTeatro(teatro.get());
        }
        
        if (obraId != null) {
            Optional<ObraDeTeatroEntity> obra = obraRepository.findById(obraId);
            if (obra.isEmpty()) {
                throw new EntityNotFoundException("La obra de teatro especificada no existe.");
            }
            resena.setObraDeTeatro(obra.get());
        }
        
        return resenaRepository.save(resena);
    }
    
    @Transactional
    public ResenaEntity obtenerResena(Long resenaId) throws EntityNotFoundException {
        Optional<ResenaEntity> resena = resenaRepository.findById(resenaId);
        if (resena.isEmpty()) {
            throw new EntityNotFoundException("Reseña no encontrada.");
        }
        return resena.get();
    }

    @Transactional
    public List<ResenaEntity> obtenerResenas() throws EntityNotFoundException {
        log.info("Inicia proceso de consultar todas las reseñas");
        List<ResenaEntity> resenas = resenaRepository.findAll();
        if (resenas.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron reseñas.");
        }
        log.info("Termina proceso de consultar todas las reseñas");
        return resenas;
    }
    
    @Transactional
    public void eliminarResena(Long resenaId) throws EntityNotFoundException {
        Optional<ResenaEntity> resenaExistenteOpt = resenaRepository.findById(resenaId);
        if (!resenaExistenteOpt.isPresent()) {
            throw new EntityNotFoundException("Resena not found");
        }

        resenaRepository.deleteById(resenaId);
    }

    @Transactional
    public ResenaEntity actualizarResena(Long resenaId, ResenaEntity nuevaResena) throws EntityNotFoundException {
        Optional<ResenaEntity> resenaExistenteOpt = resenaRepository.findById(resenaId);
        if (!resenaExistenteOpt.isPresent()) {
            throw new EntityNotFoundException("Resena not found");
        }

        ResenaEntity resenaExistente = resenaExistenteOpt.get();
        resenaExistente.setCalificacion(nuevaResena.getCalificacion());
        resenaExistente.setComentario(nuevaResena.getComentario());
        resenaExistente.setCliente(nuevaResena.getCliente());
        resenaExistente.setObraDeTeatro(nuevaResena.getObraDeTeatro());

        return resenaRepository.save(resenaExistente);
    }

    @Transactional
    public ResenaEntity buscarResenaPorClienteYObra(Long clienteId, Long obraId) throws EntityNotFoundException {
        Optional<ClienteEntity> cliente = clienteRepository.findById(clienteId);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("El cliente especificado no existe.");
        }
        
        Optional<ObraDeTeatroEntity> obra = obraRepository.findById(obraId);
        if (obra.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro especificada no existe.");
        }
        
        Optional<ResenaEntity> resena = resenaRepository.findByClienteAndObraDeTeatro(cliente.get(), obra.get());
        if (resena.isEmpty()) {
            throw new EntityNotFoundException("Reseña no encontrada.");
        }
        return resena.get();
    }

    @Transactional
    public ResenaEntity buscarResenaPorClienteYTeatro(Long clienteId, Long teatroId) throws EntityNotFoundException {
        Optional<ClienteEntity> cliente = clienteRepository.findById(clienteId);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("El cliente especificado no existe.");
        }
        
        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro especificado no existe.");
        }
        
        Optional<ResenaEntity> resena = resenaRepository.findByClienteAndTeatro(cliente.get(), teatro.get());
        if (resena.isEmpty()) {
            throw new EntityNotFoundException("Reseña no encontrada.");
        }
        return resena.get();
    }

    @Transactional
    public List<ResenaEntity> buscarResenasPorCliente(Long clienteId) throws EntityNotFoundException {
        Optional<ClienteEntity> cliente = clienteRepository.findById(clienteId);
        if (cliente.isEmpty()) {
            throw new EntityNotFoundException("El cliente especificado no existe.");
        }
        
        return resenaRepository.findByCliente(cliente.get());
    }

    @Transactional
    public List<ResenaEntity> buscarResenasPorObra(Long obraId) throws EntityNotFoundException {
        Optional<ObraDeTeatroEntity> obra = obraRepository.findById(obraId);
        if (obra.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro especificada no existe.");
        }
        
        return resenaRepository.findByObraDeTeatro(obra.get());
    }

    @Transactional
    public List<ResenaEntity> buscarResenasPorTeatro(Long teatroId) throws EntityNotFoundException {
        Optional<TeatroEntity> teatro = teatroRepository.findById(teatroId);
        if (teatro.isEmpty()) {
            throw new EntityNotFoundException("El teatro especificado no existe.");
        }
        
        return resenaRepository.findByTeatro(teatro.get());
    }

    @Transactional
    public List<ResenaEntity> buscarResenasPorCalificacion(Integer calificacion) {
        return resenaRepository.findByCalificacion(calificacion);
    }

}
