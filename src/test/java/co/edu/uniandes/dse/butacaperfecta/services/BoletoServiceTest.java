package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.BoletoRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(BoletoService.class)
class BoletoServiceTest {

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private BoletoRepository boletoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private FuncionEntity funcion;
    private AsientoEntity asiento;
    private BoletoEntity boleto;

    @BeforeEach
    void setUp() {
        funcion = factory.manufacturePojo(FuncionEntity.class);
        entityManager.persist(funcion);

        asiento = factory.manufacturePojo(AsientoEntity.class);
        entityManager.persist(asiento);

        boleto = new BoletoEntity();
        boleto.setFuncion(funcion);
        boleto.setSilla(asiento);
        entityManager.persist(boleto);
    }

    @Test
    void testCrearBoleto() throws IllegalOperationException, EntityNotFoundException {
        FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
        entityManager.persist(funcion);
    
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        entityManager.persist(cliente);
    
        AsientoEntity asiento = factory.manufacturePojo(AsientoEntity.class);
        entityManager.persist(asiento);
    
        BoletoEntity nuevoBoleto = new BoletoEntity();
        nuevoBoleto.setFuncion(funcion);
        nuevoBoleto.setCliente(cliente);
        nuevoBoleto.setSilla(asiento);
        nuevoBoleto.setPrecio(50000.0);
    
        BoletoEntity resultado = boletoService.crearBoleto(nuevoBoleto);
        
        assertNotNull(resultado);
        assertEquals(nuevoBoleto.getFuncion().getId(), resultado.getFuncion().getId());
        assertEquals(nuevoBoleto.getCliente().getId(), resultado.getCliente().getId());
        assertEquals(nuevoBoleto.getSilla().getId(), resultado.getSilla().getId());
        assertEquals(nuevoBoleto.getPrecio(), resultado.getPrecio());
    }
    

    @Test
    void testCrearBoletoDuplicado() {
        FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
        entityManager.persist(funcion);
    
        ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
        entityManager.persist(cliente);
    
        AsientoEntity asiento = factory.manufacturePojo(AsientoEntity.class);
        entityManager.persist(asiento);
    
        BoletoEntity boleto = new BoletoEntity();
        boleto.setFuncion(funcion);
        boleto.setCliente(cliente);
        boleto.setSilla(asiento);
        boleto.setPrecio(50000.0); 
        entityManager.persist(boleto);
    
        BoletoEntity boletoDuplicado = new BoletoEntity();
        boletoDuplicado.setFuncion(funcion);
        boletoDuplicado.setCliente(cliente);
        boletoDuplicado.setSilla(asiento);
        boletoDuplicado.setPrecio(50000.0);
    
        assertThrows(IllegalOperationException.class, () -> boletoService.crearBoleto(boletoDuplicado));
    }
    

    @Test
    void testObtenerBoleto() throws EntityNotFoundException {
        BoletoEntity resultado = boletoService.buscarBoleto(boleto.getId());
        assertNotNull(resultado);
        assertEquals(boleto.getId(), resultado.getId());
    }

    @Test
    void testObtenerBoletoNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> boletoService.buscarBoleto(999L));
    }

    @Test
    void testActualizarBoleto() throws IllegalOperationException, EntityNotFoundException {
        boleto.setFuncion(funcion);
        BoletoEntity resultado = boletoService.actualizarBoleto(boleto);
        assertEquals(funcion, resultado.getFuncion());
    }

    @Test
    void testActualizarBoletoNoExistente() {
        BoletoEntity boletoNoExistente = new BoletoEntity();
        boletoNoExistente.setId(999L);
        assertThrows(EntityNotFoundException.class, () -> boletoService.actualizarBoleto(boletoNoExistente));
    }

    @Test
    void testEliminarBoleto() {
        assertDoesNotThrow(() -> boletoService.eliminarBoleto(boleto.getId()));
        assertNull(entityManager.find(BoletoEntity.class, boleto.getId()));
    }

    @Test
    void testEliminarBoletoNoExistente() {
        assertThrows(EntityNotFoundException.class, () -> boletoService.eliminarBoleto(999L));
    }

    @Test
    void testObtenerTodosLosBoletos() {
        List<BoletoEntity> boletos = boletoService.obtenerTodosLosBoletos();
        assertFalse(boletos.isEmpty());
    }

    @Test
    void testObtenerFuncionDesdeBoleto() throws EntityNotFoundException {
        FuncionEntity funcionObtenida = boletoService.obtenerFuncionDeBoleto(boleto.getId());
        assertNotNull(funcionObtenida);
        assertEquals(funcion.getId(), funcionObtenida.getId());
    }

    @Test
    void testObtenerAsientoDesdeBoleto() throws EntityNotFoundException {
        AsientoEntity asientoObtenido = boletoService.obtenerAsientoDeBoleto(boleto.getId());
        assertNotNull(asientoObtenido);
        assertEquals(asiento.getId(), asientoObtenido.getId());
    }
}
