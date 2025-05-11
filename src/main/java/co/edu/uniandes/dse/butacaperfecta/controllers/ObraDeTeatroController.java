package co.edu.uniandes.dse.butacaperfecta.controllers;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.uniandes.dse.butacaperfecta.dto.FuncionDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.ObraDeTeatroDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.ObraDeTeatroDetailDTO;
import co.edu.uniandes.dse.butacaperfecta.dto.ResenaDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.FuncionEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ObraDeTeatroEntity;
import co.edu.uniandes.dse.butacaperfecta.entities.ResenaEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.services.ObraDeTeatroService;

@RestController
@RequestMapping("/obrasDeTeatro")
public class ObraDeTeatroController {

    @Autowired
    private ObraDeTeatroService obraDeTeatroService;

    @Autowired
    private ModelMapper modelMapper;

    /** 1. Obtener todas las obras */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ObraDeTeatroDetailDTO> findAll() throws EntityNotFoundException {

        List<ObraDeTeatroEntity> obras = obraDeTeatroService.obtenerTodas();
        List<ObraDeTeatroDetailDTO> obrasDTO = modelMapper.map(obras, new TypeToken<List<ObraDeTeatroDetailDTO>>() {
        }.getType());

        return obrasDTO;
    }

    /** 2. Obtener una obra por ID */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ObraDeTeatroDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException {

        ObraDeTeatroEntity obra = obraDeTeatroService.buscarObraDeTeatro(id);
        ObraDeTeatroDetailDTO obraDTO = modelMapper.map(obra, ObraDeTeatroDetailDTO.class);

        return obraDTO;
    }

    /** 3. Crear una obra de teatro */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ObraDeTeatroDTO create(@RequestBody ObraDeTeatroDTO obraDTO) throws IllegalOperationException {

        ObraDeTeatroEntity obraEntity = modelMapper.map(obraDTO, ObraDeTeatroEntity.class);
        ObraDeTeatroEntity nuevaObra = obraDeTeatroService.crearObraDeTeatro(obraEntity);
        ObraDeTeatroDTO nuevaObraDTO = modelMapper.map(nuevaObra, ObraDeTeatroDTO.class);

        return nuevaObraDTO;
    }

    /**  4. Actualizar una obra de teatro */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ObraDeTeatroDTO update(@PathVariable Long id, @RequestBody ObraDeTeatroDTO obraDTO)
            throws EntityNotFoundException, IllegalOperationException {

        ObraDeTeatroEntity obraEntity = modelMapper.map(obraDTO, ObraDeTeatroEntity.class);
        ObraDeTeatroEntity obraActualizada = obraDeTeatroService.editarObraDeTeatro(id, obraEntity);
        ObraDeTeatroDTO obraActualizadaDTO = modelMapper.map(obraActualizada, ObraDeTeatroDTO.class);

        return obraActualizadaDTO;
    }

    /** 5. Eliminar una obra de teatro */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        obraDeTeatroService.eliminarObraDeTeatro(id);
    }

    /** 6. Buscar por título */
    @GetMapping(params = "titulo")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ObraDeTeatroDTO> findByTitulo(@RequestParam String titulo)
            throws EntityNotFoundException, IllegalOperationException {
        List<ObraDeTeatroEntity> obras = obraDeTeatroService.buscarPorTitulo(titulo);
        List<ObraDeTeatroDTO> obrasDTO = modelMapper.map(obras, new TypeToken<List<ObraDeTeatroDTO>>() {
        }.getType());

        return obrasDTO;
    }

    /** 7. Buscar por género */
    @GetMapping(params = "genero")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ObraDeTeatroDTO> findByGenero(@RequestParam String genero)
            throws EntityNotFoundException, IllegalOperationException {

        List<ObraDeTeatroEntity> obras = obraDeTeatroService.buscarPorGenero(genero);
        List<ObraDeTeatroDTO> obrasDTO = modelMapper.map(obras, new TypeToken<List<ObraDeTeatroDTO>>() {
        }.getType());

        return obrasDTO;
    }

    /** 8. Asociar función */
    @PostMapping("/{obraId}/funciones/{funcionId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ObraDeTeatroDTO agregarFuncion(@PathVariable Long obraId, @PathVariable Long funcionId)
            throws EntityNotFoundException {

        ObraDeTeatroEntity obra = obraDeTeatroService.agregarFuncion(obraId, funcionId);

        ObraDeTeatroDTO obraDTO = modelMapper.map(obra, ObraDeTeatroDTO.class);
        return obraDTO;
    }

    /** 9. Eliminar función */
    @DeleteMapping("/{obraId}/funciones/{funcionId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarFuncion(@PathVariable Long obraId, @PathVariable Long funcionId)
            throws EntityNotFoundException, IllegalOperationException {

        obraDeTeatroService.eliminarFuncion(obraId, funcionId);
    }

    /** 10. Obtener funciones */
    @GetMapping("/{obraId}/funciones")
    @ResponseStatus(code = HttpStatus.OK)
    public List<FuncionDTO> obtenerFunciones(@PathVariable Long obraId) throws EntityNotFoundException {

        List<FuncionEntity> funciones = obraDeTeatroService.obtenerTodasLasFunciones(obraId);
        List<FuncionDTO> funcionesDTO = modelMapper.map(funciones, new TypeToken<List<FuncionDTO>>() {
        }.getType());

        return funcionesDTO;
    }

    /** 11. Asociar reseña */
    @PostMapping("/{obraId}/resenas/{resenaId}")
    @ResponseStatus(code = HttpStatus.OK)
    public ObraDeTeatroDTO agregarResena(@PathVariable Long obraId, @PathVariable Long resenaId)
            throws EntityNotFoundException {

        ObraDeTeatroEntity obra = obraDeTeatroService.agregarResena(resenaId, obraId);
        ObraDeTeatroDTO obraDTO = modelMapper.map(obra, ObraDeTeatroDTO.class);

        return obraDTO;
    }

    /** 12. Eliminar reseña */
    @DeleteMapping("/{obraId}/resenas/{resenaId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void eliminarResena(@PathVariable Long obraId, @PathVariable Long resenaId)
            throws EntityNotFoundException, IllegalOperationException {

        obraDeTeatroService.eliminarResena(obraId, resenaId);
    }

    /** 13. Obtener reseñas */
    @GetMapping("/{obraId}/resenas")
    @ResponseStatus(code = HttpStatus.OK)
    public List<ResenaDTO> obtenerResenas(@PathVariable Long obraId) throws EntityNotFoundException {

        List<ResenaEntity> resenas = obraDeTeatroService.obtenerResenas(obraId);
        List<ResenaDTO> resenasDTO = modelMapper.map(resenas, new TypeToken<List<ResenaDTO>>() {}.getType());

        return resenasDTO;
    }
}
