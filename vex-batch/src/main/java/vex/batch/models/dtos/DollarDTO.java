package vex.batch.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class DollarDTO {
    private String moneda;
    private String casa;
    private String nombre;
    private BigDecimal compra;
    private BigDecimal venta;
    private Date fechaActualizacion;
}
