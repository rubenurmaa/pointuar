package com.rarugames.pointuar.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(exclude = "usuario")
@Data
@NoArgsConstructor
public class Gastos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomGastos;
	private LocalDate dataVisita;

	private double gastoMensual;
	private double gastoActual;
	private double gastoEsperado;

	private double ahorroTotal;
	private double ahorroMensual;

	// Nuevos campos
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	
	private String descripcion;
	private String metodoPago;
	private String lugar;

	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private boolean esRecurrente;

	private double presupuestoAsignado;
	private double desviacion;
	private boolean pagado;

	private String moneda = "EUR";
	private boolean activo = true;
	private LocalDate fechaCreacion = LocalDate.now();

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@JsonBackReference
	private Usuario usuario;

}
