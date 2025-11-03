package com.rarugames.pointuar.controller;

import org.springframework.stereotype.Controller;
import com.rarugames.pointuar.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

}

