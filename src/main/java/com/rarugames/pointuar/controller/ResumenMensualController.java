package com.rarugames.pointuar.controller;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rarugames.pointuar.model.Ahorro;
import com.rarugames.pointuar.model.Gastos;
import com.rarugames.pointuar.model.ResumenMensual;
import com.rarugames.pointuar.repository.AhorroRepository;
import com.rarugames.pointuar.repository.GastosRepository;
import com.rarugames.pointuar.repository.ResumenMensualRepository;

@Controller
@RequestMapping("/resumenMensual")
public class ResumenMensualController {

    private final ResumenMensualRepository resumenRepo;

    @Autowired
    private GastosRepository gastoRepository;

    @Autowired
    private AhorroRepository ahorroRepository;

    public ResumenMensualController(ResumenMensualRepository resumenRepo) {
        this.resumenRepo = resumenRepo;
    }

    @GetMapping()
    public String resumenMensual(Model model) {
        // 1️⃣ Agafem tots els gastos
        List<Gastos> gastos = gastoRepository.findAll();

        // 2️⃣ Agafem tots els estalvis
        List<Ahorro> ahorros = ahorroRepository.findAll();

        // 3️⃣ Calculem el total gastat
        double gastoReal = gastos.stream()
                .mapToDouble(Gastos::getGastoActual)
                .sum();

        // 4️⃣ Calculem el total estalviat
        double ahorroTotal = ahorros.stream()
                .mapToDouble(Ahorro::getAhorroTotal)
                .sum();

        // 5️⃣ Calculem la diferència (balanç)
        double diferencia = gastoReal - ahorroTotal;

        // 6️⃣ Obtenim el mes actual en català
        String mes = LocalDate.now()
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("ca", "ES"));

        // 7️⃣ Creem l’objecte per al resum
        ResumenMensual resumen = new ResumenMensual(mes, ahorroTotal, gastoReal);
        model.addAttribute("resumen", resumen);
        model.addAttribute("diferencia", diferencia);

        return "resumen_mensual";
    }
}
