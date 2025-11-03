package com.rarugames.pointuar.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.rarugames.pointuar.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmailOrUsername(String email, String username);
}
