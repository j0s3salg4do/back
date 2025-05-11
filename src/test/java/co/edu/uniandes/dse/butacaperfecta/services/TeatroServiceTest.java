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

import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.TeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.TeatroService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(TeatroService.class)

class TeatroServiceTest {

    @Autowired
    private TeatroService teatroService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<TeatroEntity> listaTeatros = new ArrayList<>();

    @BeforeEach
    void setUp() {
        
        clearData();
        insertData();

        }
    
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from TeatroEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            TeatroEntity teatro = factory.manufacturePojo(TeatroEntity.class);
            entityManager.persist(teatro);
            if (teatro.getResenas() != null) {
                List<ResenaEntity> resenas = new ArrayList<>();
                for (ResenaEntity resena : teatro.getResenas()) {
                    resena.setTeatro(teatro);
                    entityManager.persist(resena);
                    resenas.add(resena);
                }
                teatro.setResenas(resenas);
            }
            
            listaTeatros.add(teatro);
        }
    }

    @Test
    
    void testCrearTeatro() throws IllegalOperationException {
        

        TeatroEntity newEntity = factory.manufacturePojo(TeatroEntity.class);
        TeatroEntity result = teatroService.crearTeatro(newEntity);
        assertNotNull(result);
    
        TeatroEntity entity = entityManager.find(TeatroEntity.class, result.getId());
        assertEquals(newEntity.getNombreTeatro(), entity.getNombreTeatro());

    }

    @Test
    void testCrearTeatroInvalido() {
        
        TeatroEntity newEntity = new TeatroEntity();
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.crearTeatro(newEntity);
        });
    }

    @Test
    void testEditarTeatro() throws EntityNotFoundException, IllegalOperationException {
        
        TeatroEntity entity = listaTeatros.get(0);
        TeatroEntity pojoEntity = factory.manufacturePojo(TeatroEntity.class);
        pojoEntity.setId(entity.getId());
        teatroService.editarTeatro(entity.getId(), pojoEntity);
        TeatroEntity resp = entityManager.find(TeatroEntity.class, entity.getId());
        assertEquals(pojoEntity.getNombreTeatro(), resp.getNombreTeatro());
        assertEquals(pojoEntity.getDireccionTeatro(), resp.getDireccionTeatro());
        assertEquals(pojoEntity.getCapacidad(), resp.getCapacidad());
    }

    @Test
    void testEditarTeatroInvalido() {
        
        TeatroEntity entity = listaTeatros.get(0);
        TeatroEntity pojoEntity = new TeatroEntity();
        pojoEntity.setId(entity.getId());
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.editarTeatro(entity.getId(), pojoEntity);
        });
    }

    @Test
    void testBuscarTeatro() throws EntityNotFoundException {
        
        TeatroEntity entity = listaTeatros.get(0);
        TeatroEntity newEntity = teatroService.buscarTeatro(entity.getId());
        assertNotNull(newEntity);
        assertEquals(entity.getNombreTeatro(), newEntity.getNombreTeatro());
        assertEquals(entity.getDireccionTeatro(), newEntity.getDireccionTeatro());
        assertEquals(entity.getCapacidad(), newEntity.getCapacidad());
    }

    @Test
    void testEliminarTeatro() throws EntityNotFoundException {
        
        TeatroEntity entity = listaTeatros.get(0);
        teatroService.eliminarTeatro(entity.getId());
        TeatroEntity deleted = entityManager.find(TeatroEntity.class, entity.getId());
        assertNull(deleted);
    }

    @Test
    void testEliminarTeatroInvalido() {
        
        assertThrows(EntityNotFoundException.class, () -> {
            teatroService.eliminarTeatro(Long.MAX_VALUE);
        });
    }

    @Test
    void testCrearTeatro_NombreDuplicado() {
        TeatroEntity nuevoTeatro = new TeatroEntity();
        nuevoTeatro.setNombreTeatro(listaTeatros.get(0).getNombreTeatro());
        
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.crearTeatro(nuevoTeatro);
        });
    }

    @Test
    void testCrearTeatro_DireccionDuplicada() {
        TeatroEntity nuevoTeatro = new TeatroEntity();
        nuevoTeatro.setDireccionTeatro(listaTeatros.get(0).getDireccionTeatro());
        
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.crearTeatro(nuevoTeatro);
        });
    }

    @Test
    void testEditarTeatro_NombreDuplicado() {
        TeatroEntity nuevoTeatro = new TeatroEntity();
        nuevoTeatro.setNombreTeatro(listaTeatros.get(1).getNombreTeatro());
        
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.editarTeatro(listaTeatros.get(0).getId(), nuevoTeatro);
        });
    }

    @Test
    void testEditarTeatro_DireccionDuplicada() {
        TeatroEntity nuevoTeatro = new TeatroEntity();
        nuevoTeatro.setDireccionTeatro(listaTeatros.get(1).getDireccionTeatro());
        
        assertThrows(IllegalOperationException.class, () -> {
            teatroService.editarTeatro(listaTeatros.get(0).getId(), nuevoTeatro);
        });
    }    
}
