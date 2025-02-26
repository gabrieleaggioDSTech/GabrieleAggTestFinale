package com.demo.object.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double prezzo;
}
