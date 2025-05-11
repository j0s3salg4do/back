package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.uniandes.dse.butacaperfecta.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.butacaperfecta.dto.ClienteDTO;
import co.edu.uniandes.dse.butacaperfecta.entities.ClienteEntity;
import co.edu.uniandes.dse.butacaperfecta.exceptions.EntityNotFoundException;
import org.modelmapper.TypeToken;
import co.edu.uniandes.dse.butacaperfecta.services.ClienteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;




@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClienteDTO> findAll() {
        List<ClienteEntity> clientes = clienteService.getClientes();
        return modelMapper.map(clientes, new TypeToken<List<ClienteDTO>>() {}.getType());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO findOne(@PathVariable Long id) throws EntityNotFoundException {
        ClienteEntity clienteEntity = clienteService.findById(id);
        return modelMapper.map(clienteEntity, ClienteDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO create(@Valid @RequestBody ClienteDTO clienteDTO) throws IllegalOperationException {
        ClienteEntity clienteEntity = clienteService.crearUsuario(modelMapper.map(clienteDTO, ClienteEntity.class));
        return modelMapper.map(clienteEntity, ClienteDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO update(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) throws EntityNotFoundException {
        ClienteEntity clienteEntity = clienteService.update(id, modelMapper.map(clienteDTO, ClienteEntity.class));
        return modelMapper.map(clienteEntity, ClienteDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, IllegalOperationException {
        clienteService.delete(id);
    }
}
