package com.rarugames.pointuar.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RankingGourmet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomLocal;
    private String ubicacio;
    private LocalDate dataVisita;
    private String menu;
    private double preuTotal;

    private int menjar;
    private int rapidesa;
    private int quantitat;
    private int preu;

    // Getter calculats
    public double getMitjana() {
        return (menjar + rapidesa + quantitat + preu) / 4.0;
    }

    // Getters i setters
    public Long getId() { return id; }
    public void setId(Long id) {this.id = id;}

	public String getNomLocal() { return nomLocal; }
    public void setNomLocal(String nomLocal) { this.nomLocal = nomLocal; }

    public String getUbicacio() { return ubicacio; }
    public void setUbicacio(String ubicacio) { this.ubicacio = ubicacio; }

    public LocalDate getDataVisita() { return dataVisita; }
    public void setDataVisita(LocalDate dataVisita) { this.dataVisita = dataVisita; }

    public String getMenu() { return menu; }
    public void setMenu(String menu) { this.menu = menu; }

    public double getPreuTotal() { return preuTotal; }
    public void setPreuTotal(double preuTotal) { this.preuTotal = preuTotal; }

    public int getMenjar() { return menjar; }
    public void setMenjar(int menjar) { this.menjar = menjar; }

    public int getRapidesa() { return rapidesa; }
    public void setRapidesa(int rapidesa) { this.rapidesa = rapidesa; }

    public int getQuantitat() { return quantitat; }
    public void setQuantitat(int quantitat) { this.quantitat = quantitat; }

    public int getPreu() { return preu; }
    public void setPreu(int preu) { this.preu = preu; }
}
