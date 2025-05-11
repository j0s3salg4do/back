package co.edu.uniandes.dse.butacaperfecta.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.repositories.FuncionRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ObraDeTeatroRepository;
import co.edu.uniandes.dse.butacaperfecta.repositories.ResenaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class ObraDeTeatroService {

    @Autowired
    private ObraDeTeatroRepository obraDeTeatroRepository;

    @Autowired
    private FuncionRepository funcionRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Transactional

    public ObraDeTeatroEntity crearObraDeTeatro(ObraDeTeatroEntity obraDeTeatro) throws IllegalOperationException {
        log.info("Inicia proceso de creación de una obra de teatro.");

        if (obraDeTeatro.getTitulo() == null || obraDeTeatro.getTitulo().isEmpty()) {
            throw new IllegalOperationException("El nombre de la obra no puede estar vacío.");
        }
        if (obraDeTeatro.getGenero() == null || obraDeTeatro.getGenero().isEmpty()) {
            throw new IllegalOperationException("El género de la obra no puede estar vacío.");
        }
        if (obraDeTeatro.getDuracion() == null || obraDeTeatro.getDuracion() <= 0) {
            throw new IllegalOperationException("La duración debe ser mayor que 0.");
        }
        if (obraDeTeatro.getDuracion() > 300) {
            throw new IllegalOperationException("La duración de la obra no puede ser mayor a 300 minutos.");
        }
        if (obraDeTeatro.getSinopsis() == null || obraDeTeatro.getSinopsis().isEmpty()) {
            throw new IllegalOperationException("La sinopsis de la obra no puede estar vacía.");
        }
        if (obraDeTeatro.getElenco() == null || obraDeTeatro.getElenco().isEmpty()) {
            throw new IllegalOperationException("El elenco de la obra no puede estar vacío.");
        }
        if (!obraDeTeatroRepository.findByTitulo(obraDeTeatro.getTitulo()).isEmpty()) {
            throw new IllegalOperationException("Ya existe una obra con este título.");
        }

        ObraDeTeatroEntity nuevaObra = obraDeTeatroRepository.save(obraDeTeatro);

        log.info("Finaliza proceso de creación de una obra de teatro con id = {}", nuevaObra.getId());
        return nuevaObra;
    }

    @Transactional
    
    public void eliminarObraDeTeatro(Long obraDeTeatroID) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminación de obra de teatro con id= {}", obraDeTeatroID);

        if (obraDeTeatroID == null) {
            throw new IllegalOperationException("El ID de la obra de teatro no puede ser nulo.");
        }

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroID);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra no fue encontrada.");
        }

        ObraDeTeatroEntity obra = obraOptional.get();

        // Validación: No permitir eliminar si tiene funciones asociadas
        if (!obra.getFunciones().isEmpty()) {
            throw new IllegalOperationException(
                    "No se puede eliminar la obra de teatro porque tiene funciones asociadas.");
        }

        // Validación: No permitir eliminar si tiene reseñas asociadas
        if (!obra.getResenas().isEmpty()) {
            throw new IllegalOperationException(
                    "No se puede eliminar la obra de teatro porque tiene reseñas asociadas.");
        }

        // Si pasa las validaciones, eliminar
        obraDeTeatroRepository.deleteById(obraDeTeatroID);
        log.info("Finaliza el proceso de eliminación de obra de teatro con id: {}", obraDeTeatroID);
    }

    @Transactional

    public ObraDeTeatroEntity buscarObraDeTeatro(Long obraDeTeatroID) throws EntityNotFoundException {

        log.info("Inicia proceso de búsqueda de obra de teatro por id= {}", obraDeTeatroID);
        Optional<ObraDeTeatroEntity> obra = obraDeTeatroRepository.findById(obraDeTeatroID);
        if (obra.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }
        log.info("Finaliza el proceso de búsqueda de la obra de teatro con id: {}", obraDeTeatroID);
        return obra.get();
    }

    @Transactional

    public List<ObraDeTeatroEntity> obtenerTodas() throws EntityNotFoundException {

        log.info("Inicia proceso de obtener todas las obras de teatro.");
        List<ObraDeTeatroEntity> obras = obraDeTeatroRepository.findAll();
        if (obras.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron obras de teatro.");
        }
        log.info("Finaliza el proceso de obtención de las obras de teatro");
        return obras;
    }

    @Transactional
    public void borrarTodas() {
        log.info("Inicia el proceso de borrar todas las obras de teatro");

        long total = obraDeTeatroRepository.count();
        if (total == 0) {
            log.info("No se encontraron obras de teatro para borrar.");
            return;
        }

        obraDeTeatroRepository.deleteAll();
        log.info("Se borraron todas las obras de teatro. Total eliminadas: {}", total);
    }

    @Transactional

    public List<ObraDeTeatroEntity> buscarPorGenero(String genero)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de búsqueda de obras de teatro por género");

        if (genero == null || genero.isEmpty()) {
            throw new IllegalOperationException("El género no puede estar vacío.");
        }
        List<ObraDeTeatroEntity> obrasPorGenero = obraDeTeatroRepository.findByGenero(genero);
        if (obrasPorGenero.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron obras de teatro para el genero: " + genero);
        }
        log.info("Finaliza el proceso de obtención de las obras de teatro por género");
        return obrasPorGenero;

    }

    @Transactional

    public List<ObraDeTeatroEntity> buscarPorTitulo(String titulo)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de búsqueda de obras de teatro por título");

        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalOperationException("El título no puede estar vacío.");
        }
        List<ObraDeTeatroEntity> obrasPorTitulo = obraDeTeatroRepository.findByTitulo(titulo);
        if (obrasPorTitulo.isEmpty()) {
            throw new EntityNotFoundException("No se encontraron obras de teatro para el título: " + titulo);
        }
        log.info("Finaliza el proceso de obtención de las obras de teatro por título");
        return obrasPorTitulo;

    }

    @Transactional

    public List<ObraDeTeatroEntity> buscarPorGeneroAndDuracion(String genero, Integer duracion)
            throws IllegalOperationException, EntityNotFoundException {
        log.info("Inicia proceso de búsqueda de obras de teatro por género y duración");

        if (genero == null || genero.isEmpty()) {
            throw new IllegalOperationException("El género no puede estar vacío.");
        }
        if (duracion == null || duracion <= 0) {
            throw new IllegalOperationException("La duración debe ser un número positivo.");
        }

        List<ObraDeTeatroEntity> obras = obraDeTeatroRepository.findByGeneroAndDuracion(genero, duracion);

        if (obras.isEmpty()) {
            throw new EntityNotFoundException(
                    "No se encontraron obras de teatro para el género y duración especificados.");
        }
        return obras;
    }

    @Transactional

    public ObraDeTeatroEntity editarObraDeTeatro(Long obraDeTeatroId, ObraDeTeatroEntity obraDetalles)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de edición de obra de teatro con id = {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra no fue encontrada.");
        }

        ObraDeTeatroEntity obraActualizada = obraOptional.get();

        if (obraDetalles.getTitulo() == null || obraDetalles.getTitulo().isEmpty()) {
            throw new IllegalOperationException("El título de la obra no puede estar vacío.");
        }
        if (obraDetalles.getGenero() == null || obraDetalles.getGenero().isEmpty()) {
            throw new IllegalOperationException("El género de la obra no puede estar vacío.");
        }
        if (obraDetalles.getDuracion() == null || obraDetalles.getDuracion() <= 0) {
            throw new IllegalOperationException("La duración debe ser mayor que 0.");
        }
        if (obraDetalles.getDuracion() > 300) {
            throw new IllegalOperationException("La duración de la obra no puede ser mayor a 300 minutos.");
        }
        if (obraDetalles.getSinopsis() == null || obraDetalles.getSinopsis().isEmpty()) {
            throw new IllegalOperationException("La sinopsis de la obra no puede estar vacía.");
        }
        if (obraDetalles.getElenco() == null || obraDetalles.getElenco().isEmpty()) {
            throw new IllegalOperationException("El elenco de la obra no puede estar vacío.");
        }

        obraActualizada.setTitulo(obraDetalles.getTitulo());
        obraActualizada.setGenero(obraDetalles.getGenero());
        obraActualizada.setDuracion(obraDetalles.getDuracion());
        obraActualizada.setSinopsis(obraDetalles.getSinopsis());
        obraActualizada.setElenco(obraDetalles.getElenco());

        log.info("Finaliza proceso de edición de obra de teatro con id = {}", obraDeTeatroId);

        return obraDeTeatroRepository.save(obraActualizada);
    }

    @Transactional
    public List<ObraDeTeatroEntity> buscarPorDuracionEntre(int minDuracion, int maxDuracion) {
        log.info("Inicia el proceso de búsqueda por duración de una obra de teatro");

        log.info("Finaliza el proceso de búsqueda por duración de una obra de teatro");
        return obraDeTeatroRepository.findByDuracionBetween(minDuracion, maxDuracion);

    }

    @Transactional
    public List<ObraDeTeatroEntity> palabraClaveEnSinopsis(String palabraClave) {

        log.info("Inicia el proceso de búsqueda por palabras clave de una obra de teatro");

        log.info("Finazliza el proceso de búsqueda por palabras clase de una obra de teatro");

        return obraDeTeatroRepository.findBySinopsisContainingIgnoreCase(palabraClave);
    }

    // funcion y reseñas

    @Transactional

    public ObraDeTeatroEntity agregarFuncion(Long obraDeTeatroId, Long funcionId) throws EntityNotFoundException {

        log.info("Inicia el proceso de agregado de una función a una obra de teatro con id {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        Optional<FuncionEntity> funcionOptional = funcionRepository.findById(funcionId);
        if (funcionOptional.isEmpty()) {
            throw new EntityNotFoundException("La función no fue encontrada.");
        }

        ObraDeTeatroEntity obra = obraOptional.get();
        FuncionEntity funcion = funcionOptional.get();
        obra.getFunciones().add(funcion);
        funcion.setObraDeTeatro(obra);

        log.info("Finaliza proceso de agregar función a obra de teatro con id = {}", obraDeTeatroId);
        return obraDeTeatroRepository.save(obra);
    }

    @Transactional
    public void eliminarFuncion(Long obraDeTeatroId, Long funcionId)
            throws EntityNotFoundException, IllegalOperationException {

        log.info("Inicia el proceso de eliminación de una función a una obra de teatro con id {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        Optional<FuncionEntity> funcionOptional = funcionRepository.findById(funcionId);
        if (funcionOptional.isEmpty()) {
            throw new EntityNotFoundException("La función no fue encontrada.");
        }

        ObraDeTeatroEntity obra = obraOptional.get();
        FuncionEntity funcion = funcionOptional.get();

        if (!obra.getFunciones().contains(funcion)) {
            throw new IllegalOperationException("La función no está asociada a esta obra de teatro.");
        }

        obra.getFunciones().remove(funcion);
        funcion.setObraDeTeatro(null);

        log.info("Finaliza proceso de eliminar función a obra de teatro con id = {}", obraDeTeatroId);

    }

    @Transactional
    public List<FuncionEntity> obtenerTodasLasFunciones(Long obraDeTeatroId) throws EntityNotFoundException {

        log.info("Inicia el proceso de obtención de todas las funciones de una obra de teatro con id {}",
                obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        log.info("Finaliza proceso de obtener funciones de la obra de teatro con id = {}", obraDeTeatroId);
        return obraOptional.get().getFunciones();

    }

    @Transactional

    public ObraDeTeatroEntity agregarResena(Long resenaId, Long obraDeTeatroId) throws EntityNotFoundException {

        log.info("Inicia el proceso de agregado de una reseña a una obra de teatro con id {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        Optional<ResenaEntity> resenaOptional = resenaRepository.findById(resenaId);
        if (resenaOptional.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada.");
        }

        ObraDeTeatroEntity obra = obraOptional.get();
        ResenaEntity resena = resenaOptional.get();
        obra.getResenas().add(resena);
        resena.setObraDeTeatro(obra);

        log.info("Finaliza proceso de agregar reseña a obra de teatro con id = {}", obraDeTeatroId);
        return obraDeTeatroRepository.save(obra);
    }

    @Transactional
    public void eliminarResena(Long obraDeTeatroId, Long resenaId)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de eliminación de una reseña de la obra de teatro con id {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        Optional<ResenaEntity> resenaOptional = resenaRepository.findById(resenaId);
        if (resenaOptional.isEmpty()) {
            throw new EntityNotFoundException("La reseña no fue encontrada.");
        }

        ObraDeTeatroEntity obra = obraOptional.get();
        ResenaEntity resena = resenaOptional.get();

        if (!obra.getResenas().contains(resena)) {
            throw new IllegalOperationException("La reseña no está asociada a esta obra de teatro.");
        }

        obra.getResenas().remove(resena);
        resenaRepository.delete(resena);

        log.info("Finaliza proceso de eliminación de reseña en la obra de teatro con id {}", obraDeTeatroId);
    }

    @Transactional
    public List<ResenaEntity> obtenerResenas(Long obraDeTeatroId) throws EntityNotFoundException {
        log.info("Inicia proceso de obtención de reseñas de la obra de teatro con id {}", obraDeTeatroId);

        Optional<ObraDeTeatroEntity> obraOptional = obraDeTeatroRepository.findById(obraDeTeatroId);
        if (obraOptional.isEmpty()) {
            throw new EntityNotFoundException("La obra de teatro no fue encontrada.");
        }

        List<ResenaEntity> resenas = obraOptional.get().getResenas();
        if (resenas.isEmpty()) {
            throw new EntityNotFoundException(
                    "No se encontraron reseñas para la obra de teatro con id: " + obraDeTeatroId);
        }

        log.info("Finaliza proceso de obtención de reseñas de la obra de teatro con id {}", obraDeTeatroId);
        return resenas;
    }

}
