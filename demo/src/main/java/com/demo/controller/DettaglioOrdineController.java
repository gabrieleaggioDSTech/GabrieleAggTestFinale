package com.demo.controller;

import com.demo.object.dto.DettaglioOrdineDTO;
import com.demo.service.DettaglioOrdine.DettaglioOrdineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dettagli-ordini")
public class DettaglioOrdineController {
    private final DettaglioOrdineService dettaglioOrdineService;

    public DettaglioOrdineController(DettaglioOrdineService dettaglioOrdineService) {
        this.dettaglioOrdineService = dettaglioOrdineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DettaglioOrdineDTO> getDettaglioOrdine(@PathVariable Long id) {
        return ResponseEntity.ok(dettaglioOrdineService.getDettaglioOrdineById(id));
    }

    @GetMapping
    public ResponseEntity<List<DettaglioOrdineDTO>> getAllDettagliOrdini() {
        return ResponseEntity.ok(dettaglioOrdineService.getAllDettagliOrdini());
    }

    @PostMapping
    public ResponseEntity<DettaglioOrdineDTO> createDettaglioOrdine(@RequestBody DettaglioOrdineDTO dettaglioOrdineDTO) {
        return ResponseEntity.ok(dettaglioOrdineService.createDettaglioOrdine(dettaglioOrdineDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DettaglioOrdineDTO> updateDettaglioOrdine(@PathVariable Long id, @RequestBody DettaglioOrdineDTO dettaglioOrdineDTO) {
        return ResponseEntity.ok(dettaglioOrdineService.updateDettaglioOrdine(id, dettaglioOrdineDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDettaglioOrdine(@PathVariable Long id) {
        dettaglioOrdineService.deleteDettaglioOrdine(id);
        return ResponseEntity.noContent().build();
    }
}
