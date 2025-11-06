package com.rarugames.pointuar.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.YearMonth;

@Entity
@Data
@NoArgsConstructor
public class ResumenMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mes; // Guardamos el mes como texto tipo "2025-11"
    private double gastoEsperado;
    private double gastoReal;
    
    public ResumenMensual(String mes, double gastoEsperado, double gastoReal) {
        this.mes = mes;
        this.gastoEsperado = gastoEsperado;
        this.gastoReal = gastoReal;
    }
}
