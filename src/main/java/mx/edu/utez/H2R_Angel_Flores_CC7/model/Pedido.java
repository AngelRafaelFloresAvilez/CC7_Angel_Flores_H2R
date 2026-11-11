package mx.edu.utez.H2R_Angel_Flores_CC7.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private EstadoPedido estado;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
