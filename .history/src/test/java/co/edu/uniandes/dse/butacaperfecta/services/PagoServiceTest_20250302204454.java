package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.boot.test.mock.mockito.MockBean;
import co.edu.uniandes.dse.butacaperfecta.repositories.PagoRepository;

import co.edu.uniandes.dse.butacaperfecta.entities.PagoEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.PagoService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(PagoService.class)
class PagoServiceTest {

    @Autowired
    private PagoService pagoService;

    @Autowired
    @MockBean
    private PagoRepository pagoRepository;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<PagoEntity> pagoList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM BoletoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM PagoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoEntity pagoEntity = factory.manufacturePojo(PagoEntity.class);
            pagoEntity.setMetodoPago("Tarjeta de Crédito");
            entityManager.persist(pagoEntity);
            pagoList.add(pagoEntity);
        }
    }

    /**
     * Prueba para validar métodos de pago válidos.
     */
    @Test
    void testValidarMetodoPagoValido() {
        assertDoesNotThrow(() -> pagoService.procesarPago("Tarjeta de Crédito"));
        assertDoesNotThrow(() -> pagoService.procesarPago("Tarjeta de Débito"));
        assertDoesNotThrow(() -> pagoService.procesarPago("PSE"));
        assertDoesNotThrow(() -> pagoService.procesarPago("Efectivo"));
    }

    /**
     * Prueba para validar métodos de pago no válidos.
     */
    @Test
    void testValidarMetodoPagoInvalido() {
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("Bitcoin"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("Cheque"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("PayPal"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago(""));
    }

        /**
     * Prueba para procesar un pago con un método válido.
     */
    /**
     * Prueba para procesar un pago con un método válido.
     */
    @Test
    void testProcesarPagoMetodoValido() {
        assertDoesNotThrow(() -> pagoService.procesarPago("Tarjeta de Crédito"));
        assertDoesNotThrow(() -> pagoService.procesarPago("Tarjeta de Débito"));
        assertDoesNotThrow(() -> pagoService.procesarPago("PSE"));
        assertDoesNotThrow(() -> pagoService.procesarPago("Efectivo"));
    }

    /**
     * Prueba para procesar un pago con un método no válido.
     */
    @Test
    void testProcesarPagoMetodoNoValido() {
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("Bitcoin"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("Cheque"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago("PayPal"));
        assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago(""));
        /** "assertThrows(IllegalOperationException.class, () -> pagoService.procesarPago(null));"*/
    }

        /**
     * Prueba para registrar un pago con un método de pago válido.
     */
    @Test
    void testRegistrarPagoMetodoValido() {
        PagoEntity pagoEntity = factory.manufacturePojo(PagoEntity.class);
        pagoEntity.setMetodoPago("Tarjeta de Crédito");

        assertDoesNotThrow(() -> {
            PagoEntity resultado = pagoService.registrarPago(pagoEntity);
            assertNotNull(resultado);
            assertEquals(pagoEntity.getMetodoPago(), resultado.getMetodoPago());
            assertTrue(pagoRepository.findById(resultado.getId()).isPresent());
        });
    }

    /**
     * Prueba para registrar un pago con un método de pago no válido.
     */
    @Test
    void testRegistrarPagoMetodoNoValido() {
        PagoEntity pagoEntity = factory.manufacturePojo(PagoEntity.class);
        pagoEntity.setMetodoPago("Bitcoin");

        assertThrows(IllegalOperationException.class, () -> pagoService.registrarPago(pagoEntity));

        pagoEntity.setMetodoPago("Cheque");
        assertThrows(IllegalOperationException.class, () -> pagoService.registrarPago(pagoEntity));

        pagoEntity.setMetodoPago("");
        assertThrows(IllegalOperationException.class, () -> pagoService.registrarPago(pagoEntity));

        /**pagoEntity.setMetodoPago(null);
        assertThrows(IllegalOperationException.class, () -> pagoService.registrarPago(pagoEntity));*/
    }
}

