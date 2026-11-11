package mx.edu.utez.H2R_Angel_Flores_CC7.service;

import mx.edu.utez.H2R_Angel_Flores_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.Pedido;
import mx.edu.utez.H2R_Angel_Flores_CC7.model.Producto;
import mx.edu.utez.H2R_Angel_Flores_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Angel_Flores_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyService {
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;


    public MyService(ProductoRepository productoRepository, PedidoRepository pedidoRepository) {
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido consultarDetallePedido (Long id) {

        return pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("El pedido no existe")
                );
    }


    public Producto desactivarProducto (Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException ("El producto no existe")
                );

        //No pude hacer esto
        List<Pedido> pedidos = pedidoRepository.findAllByProducto(producto);

        producto.setActivo(false);

        return productoRepository.save(producto);
    }

    public Pedido procesarPedido (Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("El pedido no existe")
                );

        if (!(pedido.getEstado() == EstadoPedido.PENDIENTE)) {
            throw new RuntimeException("Solo se pueden cancelar pedidos en PENDIENTE");
        }

        pedido.setEstado(EstadoPedido.PROCESADO);

        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido (Long id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("El pedido no existe")
                );

        if (!(pedido.getEstado() == EstadoPedido.PENDIENTE)) {
            throw new RuntimeException("Solo se pueden cancelar pedidos en PENDIENTE");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);
        int cantidadPedido = pedido.getCantidad();
        int stockActual = pedido.getProducto().getStock();
        int nuevoStock = stockActual + cantidadPedido;
        pedido.getProducto().setStock(nuevoStock);

        productoRepository.save(pedido.getProducto());

        return pedidoRepository.save(pedido);
    }

    public Pedido crearPedido (CrearPedidoDTO payload) {

        Producto producto = productoRepository.findById(payload.getId())
                .orElseThrow(
                        () -> new RuntimeException("No existe producto")
                );

        if (!producto.isActivo()) {
            throw new RuntimeException("El producto no esta activo");
        }

        if (!productoRepository.existsById(payload.getId())) {
            throw new RuntimeException("El producto no existe");
        }
        if (producto.getStock() <= 0) {
            throw new RuntimeException("No hay suficiente stock");
        }
        int stockActual = producto.getStock();
        double precioUnitario = producto.getPrecio();

        double total = precioUnitario * payload.getCantidad();
        int cantidadSolicitada = payload.getCantidad();
        int nuevoStock = stockActual - cantidadSolicitada;

        Pedido pedido = new Pedido();

        pedido.setNombreCliente(payload.getNombreCliente());
        pedido.setCantidad(cantidadSolicitada);
        pedido.setPrecioUnitario(precioUnitario);
        pedido.setTotal(total);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setProducto(producto);

        producto.setStock(nuevoStock);
        productoRepository.save(producto);

        return pedidoRepository.save(pedido);

    }
}
