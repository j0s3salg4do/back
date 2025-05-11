package co.edu.uniandes.dse.butacaperfecta.services;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.butacaperfecta.repositories.ClienteRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@Import(ClienteService.class)
class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ClienteRepository clienteRepository;

    private final PodamFactory factory = new PodamFactoryImpl();
    private ClienteEntity cliente;
  

    @BeforeEach
    void setUp() {
        cliente = factory.manufacturePojo(ClienteEntity.class);
        clienteRepository.save(cliente);
    }

    @Test
    void testCrearCliente_exitoso() throws Exception {
        ClienteEntity clienteCreado = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity clienteGuardado = clienteService.crearUsuario(clienteCreado);
        assertNotNull(clienteGuardado);
    assertEquals(clienteCreado.getId(), clienteGuardado.getId());
    }

    @Test
    void testCrearCliente_SinNombre() {
        ClienteEntity clienteSinNombre = factory.manufacturePojo(ClienteEntity.class);
        clienteSinNombre.setNombreUsuario(null);
        try {
            clienteService.crearUsuario(clienteSinNombre);
        } catch (IllegalOperationException e) {
            assertEquals("El nombre del cliente no puede estar vacío.", e.getMessage());
        }
    }


    @Test
    void testCrearCliente_SinCorreo() {
        ClienteEntity clienteSinCorreo = factory.manufacturePojo(ClienteEntity.class);
        clienteSinCorreo.setCorreoUsuario(null);
        try {
            clienteService.crearUsuario(clienteSinCorreo);
        } catch (IllegalOperationException e) {
            assertEquals("El correo no puede estar vacío.", e.getMessage());
        }
    }

    @Test
    void testCrearCliente_SinContrasena() {
        ClienteEntity clienteSinContrasena = factory.manufacturePojo(ClienteEntity.class);
        clienteSinContrasena.setContrasenaUsuario(null);
        try {
            clienteService.crearUsuario(clienteSinContrasena);
        } catch (IllegalOperationException e) {
            assertEquals("La contraseña no puede estar vacía.", e.getMessage());
        }
    }   

    @Test
    void testCrearCliente_ConCorreoInvalido() {
        ClienteEntity clienteConCorreoInvalido = factory.manufacturePojo(ClienteEntity.class);
        clienteConCorreoInvalido.setCorreoUsuario("correoInvalido");
        try {
            clienteService.crearUsuario(clienteConCorreoInvalido);
        } catch (IllegalOperationException e) {
            assertEquals("El correo del cliente no es válido", e.getMessage());
        }
    }

    @Test
    void testCrearCliente_ConContrasenaInvalida() {
        ClienteEntity clienteConContrasenaInvalida = factory.manufacturePojo(ClienteEntity.class);
        clienteConContrasenaInvalida.setContrasenaUsuario("123");
        try {
            clienteService.crearUsuario(clienteConContrasenaInvalida);
        } catch (IllegalOperationException e) {
            assertEquals("La contraseña del cliente no es válida", e.getMessage());
        }
    }

    @Test
    void testCrearCliente_ConCorreoExistente() {
        ClienteEntity clienteConCorreoExistente = factory.manufacturePojo(ClienteEntity.class);
        clienteConCorreoExistente.setCorreoUsuario(cliente.getCorreoUsuario());
        try {
            clienteService.crearUsuario(clienteConCorreoExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un cliente con el correo " + cliente.getCorreoUsuario(), e.getMessage());
        }
    }

    @Test
    void testCrearCliente_ConNombreExistente() {
        ClienteEntity clienteConNombreExistente = factory.manufacturePojo(ClienteEntity.class);
        clienteConNombreExistente.setNombreUsuario(cliente.getNombreUsuario());
        try {
            clienteService.crearUsuario(clienteConNombreExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un cliente con el nombre de usuario " + cliente.getNombreUsuario(), e.getMessage());
        }
    }

    @Test
    void testCrearCliente_ConCorreoYNombreExistente() {
        ClienteEntity clienteConCorreoYNombreExistente = factory.manufacturePojo(ClienteEntity.class);
        clienteConCorreoYNombreExistente.setCorreoUsuario(cliente.getCorreoUsuario());
        clienteConCorreoYNombreExistente.setNombreUsuario(cliente.getNombreUsuario());
        try {
            clienteService.crearUsuario(clienteConCorreoYNombreExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un cliente con este nombre.", e.getMessage());
        }
    }
    }

