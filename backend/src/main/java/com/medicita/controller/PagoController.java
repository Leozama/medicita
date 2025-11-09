package com.medicita.controller;


import com.medicita.entity.Pago;
import com.medicita.service.PagoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
//http://localhost:8080/api/pagos
@RequestMapping("/api/pagos")
public class PagoController {

   private final PagoService pagoService;

   public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
   }

   @PostMapping
    public Pago save(@RequestBody Pago pago){return pagoService.save(pago);}

    @GetMapping
    public List<Pago> findAll(){return pagoService.findAll();}

    @GetMapping("/{id}")
    public Pago findById(@PathVariable Integer id){return pagoService.findById(id);}

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){pagoService.deleteById(id);}

    @PutMapping
    public Pago update(@RequestBody Pago pago){
       Pago pagoDB = pagoService.findById(pago.getId());
       pagoDB.setId(pago.getId());
       pagoDB.setId_paciente(pago.getId_paciente());
       pagoDB.setDoctor(pago.getDoctor());
       pagoDB.setFecha(pago.getFecha());
       pagoDB.setMonto(pago.getMonto());
       pagoDB.setEstado(pago.getEstado());
       pagoDB.setMetodoPago(pago.getMetodoPago());
        return pagoService.update(pagoDB);
    }
}
