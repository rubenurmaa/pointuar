package com.rarugames.pointuar.controller;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rarugames.pointuar.model.Ahorro;
import com.rarugames.pointuar.model.Gastos;
import com.rarugames.pointuar.model.ResumenMensual;
import com.rarugames.pointuar.repository.AhorroRepository;
import com.rarugames.pointuar.repository.CategoriaRepository;
import com.rarugames.pointuar.repository.GastosRepository;
import com.rarugames.pointuar.repository.ResumenMensualRepository;
import com.rarugames.pointuar.repository.UsuarioRepository;

@Controller
@RequestMapping("/gastos")
public class GastosController {

	private final GastosRepository gastosRepo;
	private final UsuarioRepository usuarioRepo;
	private final CategoriaRepository categoriaRepo;
	private final ResumenMensualRepository resumenRepo;
	@Autowired
	private AhorroRepository ahorroRepo;

	public GastosController(GastosRepository gastosRepo, UsuarioRepository usuarioRepo, CategoriaRepository cateRepo,
			ResumenMensualRepository resumRepo) {
		this.gastosRepo = gastosRepo;
		this.usuarioRepo = usuarioRepo;
		this.categoriaRepo = cateRepo;
		this.resumenRepo = resumRepo;
	}

	@GetMapping()
	public String listarGastos(Model model) {
	    model.addAttribute("gastos", gastosRepo.findAll());

	    // Obtenim l'ahorro general o el creem si no existeix
	    Ahorro ahorro = ahorroRepo.findAll().stream().findFirst().orElseGet(() -> {
	        Ahorro nuevo = new Ahorro();
	        ahorroRepo.save(nuevo);
	        return nuevo;
	    });

	    model.addAttribute("ahorro", ahorro);
	    return "gastos";
	}
	
	@PostMapping("/guardarAhorro")
	public String guardarAhorro(@ModelAttribute Ahorro ahorro) {
	    ahorroRepo.save(ahorro);
	    return "redirect:/gastos";
	}

	@GetMapping("/nuevo")
	public String mostrarFormulario(Model model) {
		model.addAttribute("gasto", new Gastos());
		model.addAttribute("usuarios", usuarioRepo.findAll());
		model.addAttribute("categorias", categoriaRepo.findAll());
		return "gastos_form";
	}

	@PostMapping("/guardar")
	public String guardarFormulario(@ModelAttribute Gastos gasto) {
		if (gasto.getId() != null) {
			Gastos existente = gastosRepo.findById(gasto.getId())
					.orElseThrow(() -> new IllegalArgumentException("Gasto no encontrado"));
			gasto.setFechaCreacion(existente.getFechaCreacion());
		} else {
			gasto.setFechaCreacion(LocalDate.now());
		}

		gastosRepo.save(gasto);
		actualizarResumenMensual(gasto.getGastoActual());
		return "redirect:/gastos";
	}

	private void actualizarResumenMensual(double cantidad) {
		YearMonth mesActual = YearMonth.now();
		String mesTexto = mesActual.toString(); // convierte a "2025-11"

		ResumenMensual resumen = resumenRepo.findByMes(mesTexto).orElseGet(() -> {
			ResumenMensual nuevo = new ResumenMensual();
			nuevo.setMes(mesTexto);
			nuevo.setGastoEsperado(0);
			nuevo.setGastoReal(0);
			return nuevo;
		});

		resumen.setGastoReal(resumen.getGastoReal() + cantidad);
		resumenRepo.save(resumen);
	}

	@GetMapping("/editar/{id}")
	public String editarGasto(@PathVariable Long id, Model model) {
		Gastos gasto = gastosRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("ID no válido"));
		model.addAttribute("gasto", gasto);
		model.addAttribute("usuarios", usuarioRepo.findAll());
		model.addAttribute("categorias", categoriaRepo.findAll());
		return "gastos_form";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminarGasto(@PathVariable Long id) {
		Gastos gasto = gastosRepo.findById(id).orElseThrow();
		actualizarResumenMensual(-gasto.getGastoActual());
		gastosRepo.deleteById(id);
		return "redirect:/gastos";
	}

}
