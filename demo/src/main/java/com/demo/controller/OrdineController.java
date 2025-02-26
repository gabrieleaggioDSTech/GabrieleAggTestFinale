package com.demo.controller;

import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.service.Ordine.OrdineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordini")
public class OrdineController {
    private final OrdineService ordineService;

    public OrdineController(OrdineService ordineService) {
        this.ordineService = ordineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdineDTO> getOrdine(@PathVariable Long id) {
        return ResponseEntity.ok(ordineService.getOrdineById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrdineDTO>> getAllOrdini() {
        return ResponseEntity.ok(ordineService.getAllOrdini());
    }

    @PostMapping
    public ResponseEntity<OrdineDTO> createOrdine(@RequestBody CreaOrdineDTO creaOrdineDTO) {
        return ResponseEntity.ok(ordineService.createOrdine(creaOrdineDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdineDTO> updateOrdine(@PathVariable Long id, @RequestBody OrdineDTO ordineDTO) {
        return ResponseEntity.ok(ordineService.updateOrdine(id, ordineDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrdine(@PathVariable Long id) {
        ordineService.deleteOrdine(id);
        return ResponseEntity.noContent().build();
    }
}
