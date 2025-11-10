package com.medicita.controller;


import com.medicita.DTO.PagoDTO;
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
    public Pago update(@RequestBody Pago pago) {
        Pago pagoDB = pagoService.findById(pago.getId());
        pagoDB.setPaciente(pago.getPaciente());
        pagoDB.setCita(pago.getCita());
        pagoDB.setMonto(pago.getMonto());
        pagoDB.setMetodoPago(pago.getMetodoPago());
        pagoDB.setEstado(pago.getEstado());
        return pagoService.update(pagoDB);
    }

    // NUEVOS ENDPOINTS CON DTO
    @PostMapping("/dto")
    public PagoDTO saveDTO(@RequestBody Pago pago) {
        return pagoService.saveDTO(pago);
    }

    @GetMapping("/dto")
    public List<PagoDTO> findAllDTO() {
        return pagoService.findAllDTO();
    }

    @GetMapping("/dto/{id}")
    public PagoDTO findByIdDTO(@PathVariable Integer id) {
        return pagoService.findByIdDTO(id);
    }

    @GetMapping("/dto/paciente/{pacienteId}")
    public List<PagoDTO> findByPacienteId(@PathVariable Integer pacienteId) {
        return pagoService.findByPacienteIdDTO(pacienteId);
    }
}
