package co.edu.uniandes.dse.butacaperfecta.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.butacaperfecta.entities.AdministradorEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.repositories.ObraDeTeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.AdministradorRepository;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;

@Slf4j
@Service
public class AdministradorService extends AdministradorEntity {

    @Autowired
    private  AdministradorRepository administradorRepository;
    @Autowired
    private TeatroRepository teatroRepository;
    @Autowired
    private ObraDeTeatroRepository obraDeTeatroRepository;

    @Transactional
    public AdministradorEntity crearAdministrador(AdministradorEntity administrador) throws IllegalOperationException {
        log.info("Inicia proceso de creación de un administrador.");

        if (administrador.getNombreUsuario() == null || administrador.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre del administrador no puede estar vacío.");
        }

        if (administrador.getContrasenaUsuario() == null || administrador.getContrasenaUsuario().isEmpty()) {
            throw new IllegalOperationException("La contraseña no puede estar vacía.");
        }

        if (administrador.getCorreoUsuario() == null || administrador.getCorreoUsuario().isEmpty()) {
            throw new IllegalOperationException("El correo no puede estar vacío.");
        }

        if (administradorRepository.findByNombreUsuarioAndCorreoUsuario(administrador.getNombreUsuario(), administrador.getCorreoUsuario()) != null) {
            throw new IllegalOperationException("Ya existe un administrador con este nombre y correo.");
        }

        AdministradorEntity nuevoAdministrador = administradorRepository.save(administrador);
        log.info("Finaliza proceso de creación de un administrador.");
        return nuevoAdministrador;
    }

    @Transactional
    public AdministradorEntity iniciarSesion(AdministradorEntity administrador) throws IllegalOperationException{
        log.info("Inicia proceso de inicio de sesión.");

        if (administrador.getNombreUsuario() == null || administrador.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre del administrador no puede estar vacío.");
        }

        if (administrador.getContrasenaUsuario() == null || administrador.getContrasenaUsuario().isEmpty()) {
            throw new IllegalOperationException("La contraseña no puede estar vacía.");
        }

        AdministradorEntity admin = administradorRepository.findByNombreUsuarioAndCorreoUsuario(administrador.getNombreUsuario(), administrador.getCorreoUsuario());
        if (admin == null) {
            throw new IllegalOperationException("No existe un administrador con este nombre y correo.");
        }
        log.info("Finaliza proceso de inicio de sesión.");
        return admin;
    }


   @Transactional
    public AdministradorEntity findById(Long id) throws EntityNotFoundException {
        Optional<AdministradorEntity> administrador = administradorRepository.findById(id);
        if (administrador.isPresent()) {
            return administrador.get();
        } else {
            throw new EntityNotFoundException("Administrador no encontrado con id: " + id);
        }
    }

    @Transactional
    public List<AdministradorEntity> getAdministradores() {
        return administradorRepository.findAll();
    }

    @Transactional
    public AdministradorEntity update(Long id, AdministradorEntity administrador) throws EntityNotFoundException {
        Optional<AdministradorEntity> administradorExistente = administradorRepository.findById(id);
        if (!administradorExistente.isPresent()) {
            throw new EntityNotFoundException("Administrador no encontrado con id: " + id);
        }

        AdministradorEntity administradorActualizado = administradorExistente.get();
        administradorActualizado.setNombreUsuario(administrador.getNombreUsuario());
        administradorActualizado.setCorreoUsuario(administrador.getCorreoUsuario());
        administradorActualizado.setContrasenaUsuario(administrador.getContrasenaUsuario());

        return administradorRepository.save(administradorActualizado);
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException, IllegalOperationException {
        Optional<AdministradorEntity> administrador = administradorRepository.findById(id);
        if (!administrador.isPresent()) {
            throw new EntityNotFoundException("Administrador no encontrado con id: " + id);
        }

        administradorRepository.delete(administrador.get());
    }


    @Transactional
    public AdministradorEntity asignarTeatro(Long adminId, Long teatroId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        TeatroEntity teatro = teatroRepository.findById(teatroId)
            .orElseThrow(() -> new EntityNotFoundException("Teatro no encontrado"));

        administrador.setTeatro(teatro);
        return administradorRepository.save(administrador);
    }

    @Transactional
    public TeatroEntity obtenerTeatro(Long adminId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        return administrador.getTeatro();
    }

    @Transactional
    public void eliminarTeatro(Long adminId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        administrador.setTeatro(null);
        administradorRepository.save(administrador);
    }


    @Transactional
    public AdministradorEntity asignarObra(Long adminId, Long obraId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        ObraDeTeatroEntity obra = obraDeTeatroRepository.findById(obraId)
            .orElseThrow(() -> new EntityNotFoundException("Obra de teatro no encontrada"));

        administrador.setObraDeTeatro(obra);
        return administradorRepository.save(administrador);
    }

    @Transactional
    public ObraDeTeatroEntity obtenerObra(Long adminId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        return administrador.getObraDeTeatro();
    }

    @Transactional
    public void eliminarObra(Long adminId) throws EntityNotFoundException {
        AdministradorEntity administrador = administradorRepository.findById(adminId)
            .orElseThrow(() -> new EntityNotFoundException("Administrador no encontrado"));

        administrador.setObraDeTeatro(null);
        administradorRepository.save(administrador);
    }
}