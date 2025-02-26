package com.demo.object.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DettaglioOrdine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer quantita;

    @Column(nullable = false)
    private Double prezzoTotale;

    @ManyToOne
    @JoinColumn(name = "ordine_id", nullable = false)
    private Ordine ordine;

    @ManyToOne
    @JoinColumn(name = "prodotto_id", nullable = false)
    private Prodotto prodotto;
}
