package co.edu.uniandes.dse.butacaperfecta.services;
import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.butacaperfecta.entities.AdministradorEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.repositories.ClienteRepository;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class ClienteService extends ClienteEntity {

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public ClienteEntity crearUsuario(ClienteEntity cliente) throws IllegalOperationException {
        log.info("Inicia proceso de creación de un cliente.");

        if (cliente.getNombreUsuario() == null || cliente.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre del cliente no puede estar vacío.");
        }

        if (cliente.getContrasenaUsuario() == null || cliente.getContrasenaUsuario().isEmpty()) {
            throw new IllegalOperationException("La contraseña no puede estar vacía.");
        }

        if (cliente.getCorreoUsuario() == null || cliente.getCorreoUsuario().isEmpty()) {
            throw new IllegalOperationException("El correo no puede estar vacío.");
        }

        if (clienteRepository.findByNombreUsuarioAndCorreoUsuario(cliente.getNombreUsuario(), cliente.getCorreoUsuario()) != null) {
            throw new IllegalOperationException("Ya existe un cliente con este nombre.");
        }

        ClienteEntity nuevoCliente = clienteRepository.save(cliente);
        log.info("Finaliza proceso de creación de un cliente.");
        return nuevoCliente;
    }

    @Transactional
    public ClienteEntity iniciarSesion(ClienteEntity cliente) throws IllegalOperationException {
        log.info("Inicia proceso de inicio de sesión.");

        if (cliente.getNombreUsuario() == null || cliente.getNombreUsuario().isEmpty()) {
            throw new IllegalOperationException("El nombre del cliente no puede estar vacío.");
        }

        if (cliente.getContrasenaUsuario() == null || cliente.getContrasenaUsuario().isEmpty()) {
            throw new IllegalOperationException("La contraseña no puede estar vacía.");
        }

        ClienteEntity usuario = clienteRepository.findByNombreUsuarioAndContrasenaUsuario(cliente.getNombreUsuario(), cliente.getContrasenaUsuario());
        if (usuario == null) {
            throw new IllegalOperationException("No se encontró un cliente con ese nombre y contraseña.");
        }
        log.info("Finaliza proceso de inicio de sesión.");
        return usuario;
    }

    @Transactional
    public ClienteEntity findById(Long id) throws EntityNotFoundException {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if (cliente.isPresent()) {
            return cliente.get();
        } else {
            throw new EntityNotFoundException("Cliente no encontrado con id: " + id);
        }
    }

    @Transactional
    public List<ClienteEntity> getClientes() {
        return clienteRepository.findAll();
    }

    @Transactional
    public ClienteEntity update(Long id, ClienteEntity cliente) throws EntityNotFoundException {
        Optional<ClienteEntity> clienteExistente = clienteRepository.findById(id);
        if (!clienteExistente.isPresent()) {
            throw new EntityNotFoundException("Cliente no encontrado con id: " + id);
        }

        ClienteEntity clienteActualizado = clienteExistente.get();
        clienteActualizado.setNombreUsuario(cliente.getNombreUsuario());
        clienteActualizado.setCorreoUsuario(cliente.getCorreoUsuario());
        clienteActualizado.setContrasenaUsuario(cliente.getContrasenaUsuario());

        return clienteRepository.save(clienteActualizado);
    }

    @Transactional
    public void delete(Long id) throws EntityNotFoundException, IllegalOperationException {
        Optional<ClienteEntity> cliente = clienteRepository.findById(id);
        if (!cliente.isPresent()) {
            throw new EntityNotFoundException("Cliente no encontrado con id: " + id);
        }

        clienteRepository.delete(cliente.get());
    }



    @Transactional
    public ClienteEntity asignarBoleta(ClienteEntity cliente, BoletoEntity boleta) {
        cliente.getBoletas().add(boleta);
        boleta.setCliente(cliente);
        clienteRepository.save(cliente);
        return cliente;

    }

    @Transactional
    public ClienteEntity asignarResena(ClienteEntity cliente, ResenaEntity resena) {
        cliente.getResenas().add(resena);
        resena.setCliente(cliente);
        clienteRepository.save(cliente);
        return cliente;
    }

    @Transactional
    public ClienteEntity suscribirse(ClienteEntity cliente) {
        cliente.setTieneSubscripcion(true);
        clienteRepository.save(cliente);
        return cliente;
    }


}
