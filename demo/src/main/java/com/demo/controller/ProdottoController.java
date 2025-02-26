package com.demo.controller;


import com.demo.object.dto.crud.ProdottoDTO;
import com.demo.service.Prodotto.ProdottoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    private final ProdottoService prodottoService;

    public ProdottoController(ProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdottoDTO> getProdotto(@PathVariable Long id) {
        return ResponseEntity.ok(prodottoService.getProdottoById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProdottoDTO>> getAllProdotti() {
        return ResponseEntity.ok(prodottoService.getAllProdotti());
    }

    @PostMapping
    public ResponseEntity<ProdottoDTO> createProdotto(@RequestBody ProdottoDTO prodottoDTO) {
        return ResponseEntity.ok(prodottoService.createProdotto(prodottoDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdottoDTO> updateProdotto(@PathVariable Long id, @RequestBody ProdottoDTO prodottoDTO) {
        return ResponseEntity.ok(prodottoService.updateProdotto(id, prodottoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProdotto(@PathVariable Long id) {
        prodottoService.deleteProdotto(id);
        return ResponseEntity.noContent().build();
    }
}

