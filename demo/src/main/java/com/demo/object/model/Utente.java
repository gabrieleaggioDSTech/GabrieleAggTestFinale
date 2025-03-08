package com.demo.object.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ordine> ordini;

}
