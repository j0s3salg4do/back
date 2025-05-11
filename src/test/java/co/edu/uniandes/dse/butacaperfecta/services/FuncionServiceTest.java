package co.edu.uniandes.dse.butacaperfecta.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
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
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.FuncionRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import jakarta.transaction.Transactional;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(FuncionService.class)
class FuncionServiceTest {

    @Autowired
    private FuncionService funcionService;

    @Autowired
    private FuncionRepository funcionRepository;

    @Autowired
    private TestEntityManager entityManager;

    private FuncionEntity funcion;
    private ObraDeTeatroEntity obra;
    private TeatroEntity teatro;

    @BeforeEach
    void setUp() {
        obra = new ObraDeTeatroEntity();
        entityManager.persist(obra);

        teatro = new TeatroEntity();
        entityManager.persist(teatro);

        funcion = new FuncionEntity();
        funcion.setFecha(new Date());
        funcion.setHora("20:00");
        funcion.setObraDeTeatro(obra);
        funcion.setTeatro(teatro);
        entityManager.persist(funcion);
    }

    @Test
    void testCrearFuncion() throws IllegalOperationException {
        FuncionEntity nuevaFuncion = new FuncionEntity();
        nuevaFuncion.setFecha(new Date());
        nuevaFuncion.setHora("21:00");
        nuevaFuncion.setObraDeTeatro(obra);
        nuevaFuncion.setTeatro(teatro);
        
        FuncionEntity resultado = funcionService.crearFuncion(nuevaFuncion);
        assertNotNull(resultado);
        assertEquals(nuevaFuncion.getHora(), resultado.getHora());
    }

    @Test
    void testCrearFuncionDuplicada() {
        FuncionEntity nuevaFuncion = new FuncionEntity();
        nuevaFuncion.setFecha(funcion.getFecha());
        nuevaFuncion.setHora(funcion.getHora());
        nuevaFuncion.setObraDeTeatro(obra);
        nuevaFuncion.setTeatro(teatro);
        
        assertThrows(IllegalOperationException.class, () -> funcionService.crearFuncion(nuevaFuncion));
    }

    @Test
    void testEliminarFuncion() {
        assertDoesNotThrow(() -> funcionService.eliminarFuncion(funcion.getId()));
        assertNull(entityManager.find(FuncionEntity.class, funcion.getId()));
    }

    @Test
    void testEliminarFuncionNoExistente() {
        assertThrows(Exception.class, () -> funcionService.eliminarFuncion(999L));
    }

    @Test
    void testGetFuncion() {
        FuncionEntity resultado = funcionService.getFuncion(funcion.getId());
        assertNotNull(resultado);
        assertEquals(funcion.getId(), resultado.getId());
    }

    @Test
    void testGetFuncionNoExistente() {
        assertThrows(Exception.class, () -> funcionService.getFuncion(999L));
    }

    @Test
    void testUpdateFuncion() throws IllegalOperationException {
        funcion.setHora("22:00");
        FuncionEntity resultado = funcionService.updateFuncion(funcion);
        assertEquals("22:00", resultado.getHora());
    }

    @Test
    void testUpdateFuncionNoExistente() {
        FuncionEntity nuevaFuncion = new FuncionEntity();
        nuevaFuncion.setId(999L);
        nuevaFuncion.setFecha(new Date());
        nuevaFuncion.setHora("23:00");
        nuevaFuncion.setObraDeTeatro(obra);
        nuevaFuncion.setTeatro(teatro);
        
        assertThrows(Exception.class, () -> funcionService.updateFuncion(nuevaFuncion));
    }

    @Test
    void testGetTodasLasFunciones() {
        List<FuncionEntity> resultado = funcionService.getTodasLasFunciones();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
void testEliminarFuncionConBoletos() {
    FuncionEntity funcionConBoletos = new FuncionEntity();
    funcionConBoletos.setFecha(new Date());
    funcionConBoletos.setHora("20:00");
    funcionConBoletos.setObraDeTeatro(obra);
    funcionConBoletos.setTeatro(teatro);
    entityManager.persist(funcionConBoletos);

    BoletoEntity boleto = new BoletoEntity();
    boleto.setFuncion(funcionConBoletos);
    boleto.setPrecio(50000.0);
    entityManager.persist(boleto);

    funcionConBoletos.getBoletos().add(boleto);
    entityManager.persist(funcionConBoletos);

    entityManager.flush();
    entityManager.clear();

    FuncionEntity funcionRecuperada = entityManager.find(FuncionEntity.class, funcionConBoletos.getId());
    assertNotNull(funcionRecuperada);
    assertFalse(funcionRecuperada.getBoletos().isEmpty(), "La función debería tener boletos asociados.");

    assertThrows(IllegalOperationException.class, () -> funcionService.eliminarFuncion(funcionConBoletos.getId()));
}


@Test
void testGetBoletosDeFuncion() {
    FuncionEntity funcion = new FuncionEntity();
    funcion.setFecha(new Date());
    funcion.setHora("20:00");
    funcion.setObraDeTeatro(obra);
    funcion.setTeatro(teatro);
    entityManager.persist(funcion);

    BoletoEntity boleto1 = new BoletoEntity();
    boleto1.setFuncion(funcion);
    boleto1.setPrecio(50000.0);
    entityManager.persist(boleto1);

    BoletoEntity boleto2 = new BoletoEntity();
    boleto2.setFuncion(funcion);
    boleto2.setPrecio(60000.0);
    entityManager.persist(boleto2);

    funcion.getBoletos().add(boleto1);
    funcion.getBoletos().add(boleto2);
    entityManager.persist(funcion);

    entityManager.flush();
    entityManager.clear();

    FuncionEntity funcionRecuperada = entityManager.find(FuncionEntity.class, funcion.getId());
    assertNotNull(funcionRecuperada);
    assertFalse(funcionRecuperada.getBoletos().isEmpty(), "La función debería tener boletos asociados.");

    List<BoletoEntity> boletos = funcionService.getBoletosByFuncion(funcion.getId());
    assertEquals(2, boletos.size(), "La función debería tener exactamente 2 boletos.");
}


}
