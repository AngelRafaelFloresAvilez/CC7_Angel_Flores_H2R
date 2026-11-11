package mx.edu.utez.H2R_Angel_Flores_CC7.controller.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoDTO {

    private Long id;
    @NotBlank(message = "El nombre del cliente no debe estar vacio")
    private String nombreCliente;
    @Positive (message = "La cantidad debe ser mayor a cero")
    private int cantidad;
}
