package com.rarugames.pointuar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.rarugames.pointuar.model.RankingGourmet;
import com.rarugames.pointuar.model.Usuario;
import com.rarugames.pointuar.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Controller
public class WebController {

    private final UsuarioRepository repo;
    
    private final BCryptPasswordEncoder passwordEncoder;

    public WebController(UsuarioRepository repo, BCryptPasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.passwordEncoder = passwordEncoder;
    }

    // GET/Mostrar formulario de REGISTRO
    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    // POST/Agregar usuario a la BD del REGISTRO
    @PostMapping("/usuarios")
    public String createUsuario(@RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String username,
                                Model model) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setUsername(username);
        // Codificar password abans de guardar
        usuario.setPassword(passwordEncoder.encode(password));
        repo.save(usuario);
        model.addAttribute("mensaje", "Usuario registrado correctamente. Ahora puedes hacer login.");
        return "login";
    }

    // GET/Mostrar formulario del LOGIN
    @GetMapping("/login")
    public String showLoginForm(@RequestParam(required = false) String mensaje, Model model) {
        if (mensaje != null) {
            model.addAttribute("mensaje", mensaje);
        }
        return "login";
    }

    // Procesar login
    @PostMapping("/login")
    public String login(@RequestParam String emailOrUsername,
                        @RequestParam String password,
                        Model model) {

        Optional<Usuario> usuarioOpt = repo.findByEmailOrUsername(emailOrUsername, emailOrUsername);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                model.addAttribute("username", usuario.getUsername());
                model.addAttribute("mensaje", "Benvingut, " + usuario.getUsername() + "!");
                model.addAttribute("tipo", "ok");
                return "home";
            }
        }

        model.addAttribute("mensaje", "Usuari o contrasenya incorrectes.");
        model.addAttribute("tipo", "error");
        return "login";
    }
}
