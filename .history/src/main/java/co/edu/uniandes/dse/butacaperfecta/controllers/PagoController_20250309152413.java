package co.edu.uniandes.dse.butacaperfecta.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniandes.dse.butacaperfecta.services.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController 
{
    @Autowired 
    private PagoService pagoService;

    @Autowired
    private ModelMapper modelMapper;

}
