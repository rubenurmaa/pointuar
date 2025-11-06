package com.rarugames.pointuar.controller;

import java.time.YearMonth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rarugames.pointuar.model.ResumenMensual;
import com.rarugames.pointuar.repository.ResumenMensualRepository;

@Controller
@RequestMapping("/resumenMensual")
public class ResumenMensualController {

	private final ResumenMensualRepository resumenRepo;

	public ResumenMensualController(ResumenMensualRepository resumenRepo) {
		this.resumenRepo = resumenRepo;
	}

	@GetMapping
	public String mostrarResumen(Model model) {
		String mesTexto = YearMonth.now().toString();

		ResumenMensual resumen = resumenRepo.findByMes(mesTexto).orElse(new ResumenMensual(mesTexto, 0.0, 0.0));

		model.addAttribute("resumen", resumen);
		return "resumen_mensual"; // nombre de la plantilla Thymeleaf
	}

}
