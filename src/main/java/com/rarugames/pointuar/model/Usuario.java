package com.rarugames.pointuar.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString(exclude = "gastos") // Evita la recursión
@Data // genera getters, setters, toString, equals, hashCode
@NoArgsConstructor
public class Usuario {
   
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	private String username;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    
    @OneToMany(mappedBy = "usuario")
    @JsonBackReference
    private List<Gastos> gastos;
}
