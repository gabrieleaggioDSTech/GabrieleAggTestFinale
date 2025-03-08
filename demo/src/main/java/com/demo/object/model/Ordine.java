package com.demo.object.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;
    private String stato;  // IN_ATTESA, SPEDITO, CONSEGNATO
    private Double totale;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DettaglioOrdine> dettagli; //aaa
}
