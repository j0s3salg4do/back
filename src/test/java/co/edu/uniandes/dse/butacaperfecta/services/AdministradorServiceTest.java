package co.edu.uniandes.dse.butacaperfecta.services;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Import;
import co.edu.uniandes.dse.butacaperfecta.repositories.AdministradorRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.butacaperfecta.entities.AdministradorEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Transactional
@Import(AdministradorService.class)
class AdministradorServiceTest {

    @Autowired
    private AdministradorService administradorService;
    @Autowired
    private AdministradorRepository administradorRepository;

    private final PodamFactory factory = new PodamFactoryImpl();
    private AdministradorEntity administrador;

  

    @BeforeEach
    void setUp() {
        administrador = factory.manufacturePojo(AdministradorEntity.class);
        administradorRepository.save(administrador);
    }

    @Test
    void testCrearAdministrador_exitoso() throws Exception {
        AdministradorEntity administradorCreado = factory.manufacturePojo(AdministradorEntity.class);
        AdministradorEntity administradorGuardado = administradorService.crearAdministrador(administradorCreado);
        assertNotNull(administradorGuardado);
    assertEquals(administradorCreado.getId(), administradorGuardado.getId());
    }

    @Test
    void testCrearAdministrador_SinNombre() {
        AdministradorEntity administradorSinNombre = factory.manufacturePojo(AdministradorEntity.class);
        administradorSinNombre.setNombreUsuario(null);
        try {
            administradorService.crearAdministrador(administradorSinNombre);
        } catch (IllegalOperationException e) {
            assertEquals("El nombre del administrador no puede estar vacío.", e.getMessage());
        }
    }


    @Test
    void testCrearAdministrador_SinCorreo() {
        AdministradorEntity administradorSinCorreo = factory.manufacturePojo(AdministradorEntity.class);
        administradorSinCorreo.setCorreoUsuario(null);
        try {
            administradorService.crearAdministrador(administradorSinCorreo);
        } catch (IllegalOperationException e) {
            assertEquals("El correo no puede estar vacío.", e.getMessage());
        }
    }

    @Test
    void testCrearAdministrador_SinContrasena() {
        AdministradorEntity administradorSinContrasena = factory.manufacturePojo(AdministradorEntity.class);
        administradorSinContrasena.setContrasenaUsuario(null);
        try {
            administradorService.crearAdministrador(administradorSinContrasena);
        } catch (IllegalOperationException e) {
            assertEquals("La contraseña no puede estar vacía.", e.getMessage());
        }
    }   

    @Test
    void testCrearAdministrador_ConCorreoInvalido() {
        AdministradorEntity administradorConCorreoInvalido = factory.manufacturePojo(AdministradorEntity.class);
        administradorConCorreoInvalido.setCorreoUsuario("correoInvalido");
        try {
            administradorService.crearAdministrador(administradorConCorreoInvalido);
        } catch (IllegalOperationException e) {
            assertEquals("El correo del administrador no es válido", e.getMessage());
        }
    }

    @Test
    void testCrearAdministrador_ConContrasenaInvalida() {
        AdministradorEntity administradorConContrasenaInvalida = factory.manufacturePojo(AdministradorEntity.class);
        administradorConContrasenaInvalida.setContrasenaUsuario("123");
        try {
            administradorService.crearAdministrador(administradorConContrasenaInvalida);
        } catch (IllegalOperationException e) {
            assertEquals("La contraseña del administrador no es válida", e.getMessage());
        }
    }

    @Test
    void testCrearAdministrador_ConCorreoExistente() {
        AdministradorEntity administradorConCorreoExistente = factory.manufacturePojo(AdministradorEntity.class);
        administradorConCorreoExistente.setCorreoUsuario(administrador.getCorreoUsuario());
        try {
            administradorService.crearAdministrador(administradorConCorreoExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un administrador con el correo " + administrador.getCorreoUsuario(), e.getMessage());
        }
    }

    @Test
    void testCrearAdministrador_ConNombreExistente() {
        AdministradorEntity administradorConNombreExistente = factory.manufacturePojo(AdministradorEntity.class);
        administradorConNombreExistente.setNombreUsuario(administrador.getNombreUsuario());
        try {
            administradorService.crearAdministrador(administradorConNombreExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un administrador con el nombre de usuario " + administrador.getNombreUsuario(), e.getMessage());
        }
    }

    @Test
    void testCrearAdministrador_ConCorreoYNombreExistente() {
        AdministradorEntity administradorConCorreoYNombreExistente = factory.manufacturePojo(AdministradorEntity.class);
        administradorConCorreoYNombreExistente.setCorreoUsuario(administrador.getCorreoUsuario());
        administradorConCorreoYNombreExistente.setNombreUsuario(administrador.getNombreUsuario());
        try {
            administradorService.crearAdministrador(administradorConCorreoYNombreExistente);
        } catch (IllegalOperationException e) {
            assertEquals("Ya existe un administrador con este nombre y correo.", e.getMessage());
        }
    }

}