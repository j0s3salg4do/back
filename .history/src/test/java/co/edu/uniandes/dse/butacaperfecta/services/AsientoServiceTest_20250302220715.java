package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.butacaperfecta.entities.AsientoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de lógica de Asientos
 */
@DataJpaTest
@Transactional
@Import(AsientoService.class)
class AsientoServiceTest {

    @Autowired
    private AsientoService asientoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private TeatroEntity teatroConAsientos;
    private TeatroEntity teatroSinAsientos;
    private List<AsientoEntity> asientos;
    private AsientoEntity asientoExistente;
    private AsientoEntity asientoOtroTeatro;
    private AsientoEntity asientoDisponible;
    private AsientoEntity asientoNoDisponible;


    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from AsientoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from TeatroEntity").executeUpdate();
    }

    private void insertData() {
    
        teatroConAsientos = factory.manufacturePojo(TeatroEntity.class);
        teatroConAsientos.setAsientos(new ArrayList<>()); // Evitar null
        entityManager.persist(teatroConAsientos);


        asientoExistente = factory.manufacturePojo(AsientoEntity.class);
        asientoExistente.setTeatro(teatroConAsientos);
        entityManager.persist(asientoExistente);
        teatroConAsientos.getAsientos().add(asientoExistente);

        TeatroEntity otroTeatro = factory.manufacturePojo(TeatroEntity.class);
        entityManager.persist(otroTeatro);

        asientoOtroTeatro = factory.manufacturePojo(AsientoEntity.class);
        asientoOtroTeatro.setTeatro(otroTeatro);
        entityManager.persist(asientoOtroTeatro);

        entityManager.merge(teatroConAsientos); 

        teatroSinAsientos = factory.manufacturePojo(TeatroEntity.class);
        entityManager.persist(teatroSinAsientos);

        asientoDisponible = factory.manufacturePojo(AsientoEntity.class);
        asientoDisponible.setDisponible(true);
        entityManager.persist(asientoDisponible);
        
        asientoNoDisponible = factory.manufacturePojo(AsientoEntity.class);
        asientoNoDisponible.setDisponible(false);
        entityManager.persist(asientoNoDisponible);
    }

    /**
     * Prueba para obtener los asientos de un teatro existente con asientos
     */
    @Test
    void testGetAsientosTeatroConAsientos() throws EntityNotFoundException {
        List<AsientoEntity> resultado = asientoService.getAsientos(teatroConAsientos.getId());
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(asientos.size(), resultado.size());
    }

    /**
     * Prueba para obtener los asientos de un teatro existente sin asientos
     */
    /**@Test
    void testGetAsientosTeatroSinAsientos() throws EntityNotFoundException {
        List<AsientoEntity> resultado = asientoService.getAsientos(teatroSinAsientos.getId());
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }*/ 

    /**
     * Prueba para obtener los asientos de un teatro no existente
     */
    @Test
    void testGetAsientosTeatroNoExistente() {
        Long idInexistente = 999L;
        assertThrows(EntityNotFoundException.class, () -> asientoService.getAsientos(idInexistente));
    }

    @Test
    void testGetAsiento_TeatroYAsientoExisten() throws EntityNotFoundException {
        AsientoEntity resultado = asientoService.getAsiento(teatroConAsientos.getId(), asientoExistente.getId());
        assertNotNull(resultado);
        assertEquals(asientoExistente.getId(), resultado.getId());
    }


    @Test
    void testGetAsiento_TeatroNoExiste() {
        Long teatroInexistenteId = 999L;
        assertThrows(EntityNotFoundException.class, () ->
            asientoService.getAsiento(teatroInexistenteId, asientoExistente.getId())
        );
    }

    /** Asiento no existente */
    @Test
    void testGetAsiento_AsientoNoExiste() {
        Long asientoInexistenteId = 999L;
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            asientoService.getAsiento(teatroConAsientos.getId(), asientoInexistenteId)
        );

        assertTrue(exception.getMessage().contains("No se encontró el asiento"));
    }

     /**  Asiento no pertenece al teatro */
     @Test
     void testGetAsiento_AsientoNoPerteneceATeatro() {
         Exception exception = assertThrows(EntityNotFoundException.class, () ->
             asientoService.getAsiento(teatroConAsientos.getId(), asientoOtroTeatro.getId())
         );
 
         assertTrue(exception.getMessage().contains("no pertenece al teatro"));
     }

         /**  Actualizar asiento en teatro existente */
    @Test
    void testUpdateAsiento_TeatroYAsientoExisten() throws EntityNotFoundException {
        AsientoEntity nuevoAsiento = factory.manufacturePojo(AsientoEntity.class);
        nuevoAsiento.setId(asientoExistente.getId()); // Mantener el mismo ID

        AsientoEntity resultado = asientoService.updateAsiento(teatroConAsientos.getId(), asientoExistente.getId(), nuevoAsiento);

        assertNotNull(resultado);
        assertEquals(nuevoAsiento.isDisponible(), resultado.isDisponible());
        assertEquals(nuevoAsiento.getNumero(), resultado.getNumero());
        assertEquals(nuevoAsiento.getUbicacion(), resultado.getUbicacion());
    }

    /** Teatro no existente */
    @Test
    void testUpdateAsiento_TeatroNoExiste() {
        Long teatroInexistenteId = 999L;
        AsientoEntity nuevoAsiento = factory.manufacturePojo(AsientoEntity.class);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            asientoService.updateAsiento(teatroInexistenteId, asientoExistente.getId(), nuevoAsiento)
        );
    
        assertTrue(exception.getMessage().contains("No se encontró el teatro"));
    }

    /** Asiento no existente */
    @Test
    void testUpdateAsiento_AsientoNoExiste() {
        Long asientoInexistenteId = 999L;
        AsientoEntity nuevoAsiento = factory.manufacturePojo(AsientoEntity.class);

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            asientoService.updateAsiento(teatroConAsientos.getId(), asientoInexistenteId, nuevoAsiento)
        );

        assertTrue(exception.getMessage().contains("No se encontró el asiento"));
    }

    /** Asiento no pertenece al teatro */
    @Test
    void testUpdateAsiento_AsientoNoPerteneceATeatro() {
        AsientoEntity nuevoAsiento = factory.manufacturePojo(AsientoEntity.class);
    
        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            asientoService.updateAsiento(teatroConAsientos.getId(), asientoOtroTeatro.getId(), nuevoAsiento)
        );
    
        assertTrue(exception.getMessage().contains("no pertenece al teatro"));
    }

    /** Asiento existente y disponible */
    @Test
    void testConsultarDisponibilidad_AsientoDisponible() throws EntityNotFoundException {
        boolean disponible = asientoService.consultarDisponibilidad(asientoDisponible.getId());
        assertTrue(disponible, "El asiento debería estar disponible.");
    }

    /**  Asiento existente y no disponible */
    @Test
    void testConsultarDisponibilidad_AsientoNoDisponible() throws EntityNotFoundException {
        boolean disponible = asientoService.consultarDisponibilidad(asientoNoDisponible.getId());
        assertFalse(disponible, "El asiento debería estar ocupado.");
    }

    /**  Asiento no existente */
    @Test
    void testConsultarDisponibilidad_AsientoNoExiste() {
        Long asientoInexistenteId = 999L;

        Exception exception = assertThrows(EntityNotFoundException.class, () ->
            asientoService.consultarDisponibilidad(asientoInexistenteId)
        );

        assertTrue(exception.getMessage().contains("No se encontró el asiento"));
    }



}

