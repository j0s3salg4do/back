package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.repositories.ClienteRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ResenaRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.TeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ObraDeTeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.services.ResenaService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@SpringBootTest
@Transactional
@Import(ResenaService.class)
class ResenaServiceTest {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private ResenaRepository resenaRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private TeatroRepository teatroRepository;
    
    @Autowired
    private ObraDeTeatroRepository obraRepository;

    private PodamFactory factory = new PodamFactoryImpl();
    private ClienteEntity cliente;
    private TeatroEntity teatro;
    private ObraDeTeatroEntity obra;
    private ResenaEntity resena;

    @BeforeEach
    void setUp() {
        cliente = clienteRepository.save(factory.manufacturePojo(ClienteEntity.class));
        teatro = teatroRepository.save(factory.manufacturePojo(TeatroEntity.class));
        obra = obraRepository.save(factory.manufacturePojo(ObraDeTeatroEntity.class));

        resena = factory.manufacturePojo(ResenaEntity.class);
        resena.setCliente(cliente);
        resena.setObraDeTeatro(obra);
        resena.setCalificacion(5);
        resena.setComentario("Excelente obra");
        resena = resenaRepository.save(resena);
    }

    @Test
    void testCrearResena_Exitoso() throws Exception {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        ResenaEntity resultado = resenaService.crearResena(nuevaResena, cliente.getId(), null, obra.getId());
        assertNotNull(resultado);
        assertEquals(nuevaResena.getComentario(), resultado.getComentario());
    }

    @Test
    void testCrearResena_SinComentario() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario(null);

        assertThrows(Exception.class, () -> resenaService.crearResena(nuevaResena, cliente.getId(), null, obra.getId()));
    }

    @Test
    void testCrearResena_SinCalificacion() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(null);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(Exception.class, () -> resenaService.crearResena(nuevaResena, cliente.getId(), null, obra.getId()));
    }

    @Test
    void testCrearResena_CalificacionInvalida() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(6);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(Exception.class, () -> resenaService.crearResena(nuevaResena, cliente.getId(), null, obra.getId()));
    }

    @Test
    void testCrearResena_ClienteNoExiste() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(EntityNotFoundException.class, () -> resenaService.crearResena(nuevaResena, 0, null, obra.getId()));
    }

    @Test
    void testCrearResena_TeatroNoExiste() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(EntityNotFoundException.class, () -> resenaService.crearResena(nuevaResena, cliente.getId(), 0L, null));
    }

    @Test
    void testCrearResena_ObraNoExiste() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(EntityNotFoundException.class, () -> resenaService.crearResena(nuevaResena, cliente.getId(), null, 0L));
    }

    @Test
    void testObtenerResena_Exitoso() throws Exception {
        ResenaEntity resultado = resenaService.obtenerResena(resena.getId());
        assertNotNull(resultado);
        assertEquals(resena.getComentario(), resultado.getComentario());
    }

    @Test
    void testObtenerResena_NoExiste() {
        assertThrows(EntityNotFoundException.class, () -> resenaService.obtenerResena(0L));
    }

    @Test
    void testEliminarResena_Exitoso() throws Exception {
        resenaService.eliminarResena(resena.getId());
        assertThrows(EntityNotFoundException.class, () -> resenaService.obtenerResena(resena.getId()));
    }

    @Test
    void testEliminarResena_NoExiste() {
        assertThrows(EntityNotFoundException.class, () -> {
            resenaService.eliminarResena(0L);
        });
    }

    @Test
    void testActualizarResena_Exitoso() throws Exception {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        ResenaEntity resultado = resenaService.actualizarResena(resena.getId(), nuevaResena);
        assertNotNull(resultado);
        assertEquals(nuevaResena.getComentario(), resultado.getComentario());
    }

    @Test
    void testActualizarResena_NoExiste() {
        ResenaEntity nuevaResena = factory.manufacturePojo(ResenaEntity.class);
        nuevaResena.setCliente(cliente);
        nuevaResena.setObraDeTeatro(obra);
        nuevaResena.setCalificacion(4);
        nuevaResena.setComentario("Muy buena obra");

        assertThrows(EntityNotFoundException.class, () -> resenaService.actualizarResena(0L, nuevaResena));
    }
}
