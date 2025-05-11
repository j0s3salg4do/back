package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.butacaperfecta.entities.BoletoEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.EventoEspecialEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EventoEspecialService.class)
class EventoEspecialServiceTest {

    @Autowired
    private EventoEspecialService eventoEspecialService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<EventoEspecialEntity> eventoEspecialList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from BoletoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EventoEspecialEntity").executeUpdate();
    }

    public void insertData() {
        for (int i = 0; i < 3; i++) {
            EventoEspecialEntity evento = factory.manufacturePojo(EventoEspecialEntity.class);
            entityManager.persist(evento);
            eventoEspecialList.add(evento);
        }
    }

    /// PRUEBAS DE CREACIÓN ///

    @Test
    @DisplayName("Crear un evento especial exitosamente")

    void testCrearEventoEspecial() throws IllegalOperationException {
        EventoEspecialEntity evento = factory.manufacturePojo(EventoEspecialEntity.class);

        evento.setNombreEvento("Festival de Teatro");
        evento.setDescripcionEvento("Un evento lleno de cultura y arte.");
        evento.setFecha(new Date());

        EventoEspecialEntity resultado = eventoEspecialService.crearEventoEspecial(evento);
        assertNotNull(resultado);
        assertNotNull(resultado.getId());

        entityManager.flush();
        entityManager.clear();
        EventoEspecialEntity entity = entityManager.find(EventoEspecialEntity.class, resultado.getId());
        assertNotNull(entity);
        assertEquals(evento.getNombreEvento(), entity.getNombreEvento());
    }

    @Test

    @DisplayName("No se puede crear un evento con descripción vacía")
    void testCrearEventoEspecialDescripcionVacia() {
        EventoEspecialEntity evento = factory.manufacturePojo(EventoEspecialEntity.class);
        evento.setDescripcionEvento("");

        assertThrows(IllegalOperationException.class, () -> eventoEspecialService.crearEventoEspecial(evento));
    }

    /// PRUEBAS DE ELIMINACIÓN ///

    @Test
    @DisplayName("No se puede eliminar un evento especial inexistente")

    void testEliminarEventoEspecialInexistente() {
        assertThrows(EntityNotFoundException.class, () -> eventoEspecialService.eliminarEventoEspecial(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Se pueden eliminar todos los eventos exitosamente")

    void testEliminarTodosLosEventos() {
        eventoEspecialService.borrarTodos();
        assertThrows(EntityNotFoundException.class, () -> eventoEspecialService.obtenerTodos());
    }

    /// PRUEBAS DE ASOCIACIÓN ///

    @Test
    @DisplayName("No se puede asociar un evento especial a un teatro inexistente")

    void testAsociarEventoATeatroInexistente() {
        EventoEspecialEntity evento = eventoEspecialList.get(0);
        assertThrows(EntityNotFoundException.class, () -> eventoEspecialService.asociarTeatro(evento.getId(), Long.MAX_VALUE));
    }

    @Test
    @DisplayName("No se puede agregar un boleto a un evento inexistente")

    void testAgregarBoletoAEventoInexistente() {
        BoletoEntity boleto = factory.manufacturePojo(BoletoEntity.class);
        entityManager.persist(boleto);
        assertThrows(EntityNotFoundException.class,() -> eventoEspecialService.agregarBoleto(Long.MAX_VALUE, boleto.getId()));
    }

    /// PRUEBAS DE BÚSQUEDA ///

    @Test
    @DisplayName("Buscar eventos por nombre exitosamente")

    void testBuscarEventoPorNombre() throws EntityNotFoundException {
        EventoEspecialEntity evento = eventoEspecialList.get(0);
        List<EventoEspecialEntity> resultado = eventoEspecialService.buscarPorTitulo(evento.getNombreEvento());
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(evento.getNombreEvento(), resultado.get(0).getNombreEvento());
    }

    @Test
    @DisplayName("Buscar eventos por rango de fechas")

    void testBuscarEventoPorRangoDeFechas() throws EntityNotFoundException {
        EventoEspecialEntity evento = eventoEspecialList.get(0);
        List<EventoEspecialEntity> resultado = eventoEspecialService.buscarEventoPorFecha(evento.getFecha());
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(evento.getFecha(), resultado.get(0).getFecha());
    }

    /// PRUEBAS DE ACTUALIZACIÓN ///

    @Test
    @DisplayName("No se puede actualizar un evento con nombre vacío")

    void testActualizarEventoEspecialNombreVacio() {
        EventoEspecialEntity evento = eventoEspecialList.get(0);
        EventoEspecialEntity eventoNuevo = factory.manufacturePojo(EventoEspecialEntity.class);
        eventoNuevo.setId(evento.getId());
        eventoNuevo.setNombreEvento("");

        assertThrows(IllegalOperationException.class, () -> eventoEspecialService.editarEventoEspecial(evento.getId(), eventoNuevo));
    }

    
}
