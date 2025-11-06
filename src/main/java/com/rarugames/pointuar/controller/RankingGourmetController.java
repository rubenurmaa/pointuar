package com.rarugames.pointuar.controller;

import com.rarugames.pointuar.model.RankingGourmet;
import com.rarugames.pointuar.repository.RankingGourmetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/ranking")
public class RankingGourmetController {

	@Autowired
	private RankingGourmetRepository repo;

	// ================= Nuevo ranking =================
	@GetMapping
	public String mostrarForm(Model model) {
		model.addAttribute("ranking", new RankingGourmet());
		return "ranking"; // template para crear
	}

	@PostMapping
	public String guardarForm(@ModelAttribute RankingGourmet ranking, Model model) {
		try {
			repo.save(ranking);
			model.addAttribute("ranking", new RankingGourmet());
			model.addAttribute("message", "✅ Valoració guardada correctament!");
			return "redirect:/ranking/list";
		} catch (Exception e) {
			model.addAttribute("ranking", ranking);
			model.addAttribute("error", "⚠️ Error en desar la valoració: " + e.getMessage());
		}
		return "ranking";
	}

	// ================= Listado =================
	@GetMapping("/list")
	public String mostrarLista(Model model) {
		model.addAttribute("locals", repo.findAll());
		return "ranking_list";
	}

	// ================= Editar =================
	@GetMapping("/edit/{id}")
	public String showEditForm(@PathVariable Long id, Model model) {
		Optional<RankingGourmet> rankingOpt = repo.findById(id);
		if (rankingOpt.isPresent()) {
			model.addAttribute("ranking", rankingOpt.get());
			return "ranking_edit"; // template para editar
		} else {
			return "redirect:/ranking/list";
		}
	}

	@PostMapping("/edit/{id}")
	public String updateRanking(@PathVariable Long id, @ModelAttribute RankingGourmet ranking, Model model) {
		try {
			ranking.setId(id);
			repo.save(ranking);
			return "redirect:/ranking/list";
		} catch (Exception e) {
			model.addAttribute("ranking", ranking);
			model.addAttribute("error", "⚠️ Error al actualizar: " + e.getMessage());
			return "ranking_edit";
		}
	}

	// ================= Eliminar =================
	@GetMapping("/delete/{id}")
	public String deleteRanking(@PathVariable Long id) {
		repo.deleteById(id);
		return "redirect:/ranking/list";
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null || text.isEmpty() ? null : LocalDate.parse(text, formatter));
			}
		});
	}
}
