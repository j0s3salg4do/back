package co.edu.uniandes.dse.butacaperfecta.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.ObraDeTeatroService;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ObraDeTeatroService.class)

class ObraDeTeatroServiceTest {

    @Autowired
    private ObraDeTeatroService obraDeTeatroService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<ObraDeTeatroEntity> obraDeTeatroList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        // Elimina todas las reseñas registradas en la base de datos
        entityManager.getEntityManager().createQuery("delete from ResenaEntity").executeUpdate();

        // Elimina todas las funciones registradas en la base de datos
        entityManager.getEntityManager().createQuery("delete from FuncionEntity").executeUpdate();

        // Elimina todas las obras de teatro registradas en la base de datos
        entityManager.getEntityManager().createQuery("delete from ObraDeTeatroEntity").executeUpdate();
    }

    public void insertData() {
        // Crea 3 instancias de obra de teatro en la base de datos
        for (int i = 0; i < 3; i++) {
            ObraDeTeatroEntity obraEntity = factory.manufacturePojo(ObraDeTeatroEntity.class);
            entityManager.persist(obraEntity);
            obraDeTeatroList.add(obraEntity);
        }
    }

    // ================= PRUEBAS DE CREACIÓN =================

    @Test
    @DisplayName("Crear una obra de teatro exitosamente")
    void testCrearObraDeTeatro() throws IllegalOperationException {

        // Crear una obra con datos válidos
        ObraDeTeatroEntity obraEntity = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraEntity.setTitulo("Obra de teatro de prueba");
        obraEntity.setGenero("Comedia");
        obraEntity.setDuracion(120);
        obraEntity.setSinopsis("Una divertida historia de enredos.");
        obraEntity.setElenco("Actor1, Actriz2, Actor3");

        // Ejecutar la creación
        ObraDeTeatroEntity resultado = obraDeTeatroService.crearObraDeTeatro(obraEntity);

        // Verificar que la obra fue creada y persistida
        assertNotNull(resultado);
        assertNotNull(resultado.getId()); // Aseguramos que tiene un ID generado

        // Recargar desde la base de datos
        entityManager.flush(); // Asegurar que la transacción se complete
        entityManager.clear(); // Limpiar caché para evitar falsos positivos
        ObraDeTeatroEntity entity = entityManager.find(ObraDeTeatroEntity.class, resultado.getId());

        // Verificar que la obra persiste correctamente
        assertNotNull(entity);
        assertEquals(obraEntity.getTitulo(), entity.getTitulo());
        assertEquals(obraEntity.getGenero(), entity.getGenero());
        assertEquals(obraEntity.getDuracion(), entity.getDuracion());
        assertEquals(obraEntity.getSinopsis(), entity.getSinopsis());
        assertEquals(obraEntity.getElenco(), entity.getElenco());
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con título vacío")

    void testCrearObraDeTeatroVacia() throws IllegalOperationException {
        ObraDeTeatroEntity obraEntity = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraEntity.setTitulo("");

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obraEntity));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con género vacío")
    void testCrearObraDeTeatroGeneroVacio() throws IllegalOperationException {
        ObraDeTeatroEntity obraEntity = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraEntity.setGenero("");

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obraEntity));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con género nulo")
    void testCrearObraDeTeatroGeneroNulo() throws IllegalOperationException {
        ObraDeTeatroEntity obraEntity = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraEntity.setGenero(null);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obraEntity));
    }

    // @Test
    // @DisplayName("No se puede crear una obra de teatro con duración menor o igual
    // a 0")
    //
    // void testCrearObraDeTeatroDuracionInvalida() throws IllegalOperationException
    // {
    // ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
    //
    // obra.setDuracion(0);
    // assertThrows(IllegalOperationException.class, () ->
    // obraDeTeatroService.crearObraDeTeatro(obra));
    //
    // obra.setDuracion(-5);
    // assertThrows(IllegalOperationException.class, () ->
    // obraDeTeatroService.crearObraDeTeatro(obra));
    // }

    @Test
    @DisplayName("No se puede crear una obra de teatro con sinopsis vacía")
    void testCrearObraDeTeatroSinopsisVacia() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setSinopsis("");

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con sinopsis nula")
    void testCrearObraDeTeatroSinopsisNula() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setSinopsis(null);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con elenco vacío")
    void testCrearObraDeTeatroElencoVacio() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setElenco("");

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con elenco nulo")
    void testCrearObraDeTeatroElencoNulo() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setElenco(null);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra de teatro con título duplicado")

    void testCrearObraDeTeatroTituloDuplicado() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setTitulo(obraDeTeatroList.get(0).getTitulo());

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra con duración negativa")
    void testCrearObraDeTeatroDuracionNegativa() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setDuracion(-50);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra con duración de 0 minutos")
    void testCrearObraDeTeatroDuracionCero() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setDuracion(0);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    @Test
    @DisplayName("No se puede crear una obra con duración irrealmente alta")
    void testCrearObraDeTeatroDuracionIrreal() throws IllegalOperationException {
        ObraDeTeatroEntity obra = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obra.setDuracion(1500);

        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.crearObraDeTeatro(obra));
    }

    // ================= PRUEBAS DE ELIMINACIÓN =================

    @Test
    @DisplayName("Eliminar una obra de teatro existente")

    void testEliminarObraDeTeatro() throws EntityNotFoundException, IllegalOperationException {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);

        obraDeTeatroService.eliminarObraDeTeatro(obraEntity.getId());

        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.buscarObraDeTeatro(obraEntity.getId()));
    }

    @Test
    @DisplayName("No se puede eliminar una obra de teatro inexistente")

    void testEliminarObraDeTeatroInexistente() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.eliminarObraDeTeatro(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("No se puede eliminar una obra de teatro si tiene funciones asociadas")

    void testEliminarObraDeTeatroConFuncionesAsociadas() throws EntityNotFoundException {
        
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);

        
        FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
        funcion.setObraDeTeatro(obraEntity);
        entityManager.persist(funcion);

        // Forzar que los cambios se reflejen en la base de datos
        entityManager.flush();
        entityManager.clear();

        
        ObraDeTeatroEntity obraRecargada = entityManager.find(ObraDeTeatroEntity.class, obraEntity.getId());

    
        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.eliminarObraDeTeatro(obraRecargada.getId()));
    }


    //
    //@Test
    //@DisplayName("No se puede eliminar una obra de teatro con reseñas asociadas")
//
    //void testEliminarObraDeTeatroConResenasAsociadas() throws EntityNotFoundException {
    //
    //    ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
//
    //    ResenaEntity resena = factory.manufacturePojo(ResenaEntity.class);
    //    resena.setObraDeTeatro(obraEntity);
    //    entityManager.persist(resena);
//
    //    // Forzar que los cambios se reflejen en la base de datos
    //    entityManager.flush();
    //    entityManager.clear();
//
    //    ObraDeTeatroEntity obraRecargada = entityManager.find(ObraDeTeatroEntity.class, obraEntity.getId());
//
    //    assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.eliminarObraDeTeatro(obraRecargada.getId()));
    //}

    @Test
    @DisplayName("Se puede eliminar una obra de teatro después de eliminar sus funciones")

    void testEliminarObraDeTeatroSinFuncionesAsociadas() throws EntityNotFoundException, IllegalOperationException {

        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);

        FuncionEntity funcion = factory.manufacturePojo(FuncionEntity.class);
        funcion.setObraDeTeatro(obraEntity);
        entityManager.persist(funcion);

        // Eliminar la función asociada antes de eliminar la obra
        entityManager.remove(funcion);

        // Intentar eliminar la obra de teatro → ahora sí debe poder eliminarse
        assertDoesNotThrow(() -> obraDeTeatroService.eliminarObraDeTeatro(obraEntity.getId()));

        // Verificar que la obra ya no existe en la base de datos
        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.buscarObraDeTeatro(obraEntity.getId()));
    }

    @Test
    @DisplayName("No se puede eliminar una obra de teatro con ID nulo")
    void testEliminarObraDeTeatroIdNulo() {
        // Intentar eliminar una obra con ID nulo → debe lanzar una excepción
        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.eliminarObraDeTeatro(null));
    }

    @Test
    @DisplayName("Eliminar todas las obras de teatro exitosamente")

    void testEliminarTodasLasObras() {

        assertFalse(obraDeTeatroList.isEmpty());

        obraDeTeatroService.borrarTodas();

        // Intentar obtener todas las obras → debe lanzar una excepción porque la lista
        // está vacía
        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.obtenerTodas());
    }

    // ================= PRUEBAS DE BÚSQUEDA =================

    @Test
    @DisplayName("Buscar una obra de teatro por ID")

    void testBuscarObraDeTeatro() throws EntityNotFoundException {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
        ObraDeTeatroEntity entity = obraDeTeatroService.buscarObraDeTeatro(obraEntity.getId());

        assertNotNull(entity);
        assertEquals(obraEntity.getTitulo(), entity.getTitulo());
        assertEquals(obraEntity.getGenero(), entity.getGenero());
        assertEquals(obraEntity.getDuracion(), entity.getDuracion());
        assertEquals(obraEntity.getSinopsis(), entity.getSinopsis());
        assertEquals(obraEntity.getElenco(), entity.getElenco());
    }

    @Test
    @DisplayName("No se puede buscar una obra de teatro inexistente")

    void testBuscarObraDeTeatroInexistente() throws EntityNotFoundException {
        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.buscarObraDeTeatro(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("Buscar todas las obras de teatro")

    void testBuscarTodasLasObrasExitosamente() throws EntityNotFoundException {
        List<ObraDeTeatroEntity> obras = obraDeTeatroService.obtenerTodas();

        assertNotNull(obras);
        assertFalse(obras.isEmpty());
        assertEquals(obraDeTeatroList.size(), obras.size());
    }

    @Test
    @DisplayName("No se puede buscar obras de teatro si no hay ninguna registrada")

    void testBuscarTodasLasObrasSinRegistros() {

        obraDeTeatroService.borrarTodas();

        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.obtenerTodas());
    }

    @Test
    @DisplayName("Buscar una obra de teatro por título exitosamente")

    void testBuscarObraPorTitulo() throws EntityNotFoundException, IllegalOperationException {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
        List<ObraDeTeatroEntity> resultado = obraDeTeatroService.buscarPorTitulo(obraEntity.getTitulo());

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(obraEntity.getTitulo(), resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("No se puede buscar una obra de teatro con un título inexistente")

    void testBuscarObraPorTituloInexistente() {
        assertThrows(EntityNotFoundException.class, () -> obraDeTeatroService.buscarPorTitulo("Título Inexistente"));
    }

    @Test
    @DisplayName("No se puede buscar una obra de teatro con un título vacío o nulo")

    void testBuscarObraPorTituloVacioONulo() {
        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.buscarPorTitulo(""));
        assertThrows(IllegalOperationException.class, () -> obraDeTeatroService.buscarPorTitulo(null));
    }

    // ================= PRUEBAS DE ACTUALIZACIÓN =================

    @Test
    @DisplayName("Actualizar una obra de teatro exitosamente")

    void testActualizarObraDeTeatro() throws EntityNotFoundException, IllegalOperationException {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);

        ObraDeTeatroEntity obraNueva = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraNueva.setId(obraEntity.getId());

        obraNueva.setTitulo("Nuevo Título");
        obraNueva.setGenero("Drama");
        obraNueva.setDuracion(120);
        obraNueva.setSinopsis("Una historia de pasión y tragedia.");
        obraNueva.setElenco("Actor1, Actor2, Actriz3");

        ObraDeTeatroEntity resultado = obraDeTeatroService.editarObraDeTeatro(obraEntity.getId(), obraNueva);

        assertNotNull(resultado);
        assertEquals(obraNueva.getTitulo(), resultado.getTitulo());
        assertEquals(obraNueva.getGenero(), resultado.getGenero());
        assertEquals(obraNueva.getDuracion(), resultado.getDuracion());
        assertEquals(obraNueva.getSinopsis(), resultado.getSinopsis());
        assertEquals(obraNueva.getElenco(), resultado.getElenco());
    }

    @Test
    @DisplayName("No se puede actualizar una obra de teatro inexistente")
    void testActualizarObraDeTeatroInexistente() {
        ObraDeTeatroEntity obraNueva = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraNueva.setId(0L);

        assertThrows(EntityNotFoundException.class,
                () -> obraDeTeatroService.editarObraDeTeatro(obraNueva.getId(), obraNueva));
    }

    @Test
    @DisplayName("No se puede actualizar una obra de teatro con título vacío")
    void testActualizarObraDeTeatroTituloVacio() {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
        ObraDeTeatroEntity obraNueva = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraNueva.setId(obraEntity.getId());
        obraNueva.setTitulo("");

        assertThrows(IllegalOperationException.class,
                () -> obraDeTeatroService.editarObraDeTeatro(obraEntity.getId(), obraNueva));
    }

    @Test
    @DisplayName("No se puede actualizar una obra de teatro con duración inválida")
    void testActualizarObraDeTeatroDuracionInvalida() {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
        ObraDeTeatroEntity obraNueva = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraNueva.setId(obraEntity.getId());
        obraNueva.setDuracion(0);

        assertThrows(IllegalOperationException.class,
                () -> obraDeTeatroService.editarObraDeTeatro(obraEntity.getId(), obraNueva));
    }

    @Test
    @DisplayName("No se puede actualizar una obra de teatro con género nulo")
    void testActualizarObraDeTeatroGeneroNulo() {
        ObraDeTeatroEntity obraEntity = obraDeTeatroList.get(0);
        ObraDeTeatroEntity obraNueva = factory.manufacturePojo(ObraDeTeatroEntity.class);
        obraNueva.setId(obraEntity.getId());
        obraNueva.setGenero(null);

        assertThrows(IllegalOperationException.class,
                () -> obraDeTeatroService.editarObraDeTeatro(obraEntity.getId(), obraNueva));
    }

}
