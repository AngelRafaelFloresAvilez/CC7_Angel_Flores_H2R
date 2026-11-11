package mx.edu.utez.H2R_Angel_Flores_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.H2R_Angel_Flores_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.Pedido;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.Producto;
import mx.edu.utez.H2R_Angel_Flores_CC7.service.MyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {

    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }


    @PostMapping ("/crear-pedido")
    public ResponseEntity<Pedido> crearPedido (@RequestBody @Valid CrearPedidoDTO payload) {
        return ResponseEntity.status(201).body(service.crearPedido(payload));
    }

    @PatchMapping ("/cancelar-pedido/{id}")
    public ResponseEntity<Pedido> cancelarPedido (@PathVariable Long id) {
        return ResponseEntity.status(200).body(service.cancelarPedido(id));
    }

    @PatchMapping ("/procesar-pedido/{id}")
    public ResponseEntity<Pedido> procesarPedido (@PathVariable Long id) {
        return ResponseEntity.status(200).body(service.procesarPedido(id));
    }

    @PatchMapping ("/desactivar-producto/{id}")
    public ResponseEntity<Producto> desactivarProducto (@PathVariable Long id) {
        return ResponseEntity.status(200).body(service.desactivarProducto(id));
    }

    @GetMapping ("/consultar-pedido/{id}")
    public ResponseEntity<Pedido> consultarDetallePedido (@PathVariable Long id) {
        return ResponseEntity.status(200).body(service.consultarDetallePedido(id));
    }

}
