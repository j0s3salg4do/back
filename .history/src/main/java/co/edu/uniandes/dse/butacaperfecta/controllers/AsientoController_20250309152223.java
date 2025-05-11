package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.butacaperfecta.services.AsientoService;

@RestController
@RequestMapping("/teatros")
public class AsientoController {
    @Autowired
    private AsientoService asientoService;

    @Autowired
    private ModelMapper modelMapper;

    

}
