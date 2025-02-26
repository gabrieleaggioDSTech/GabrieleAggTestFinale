package com.demo.controller;

import com.demo.object.dto.CreaUtenteDTO;
import com.demo.object.dto.UpdateUtenteDTO;
import com.demo.object.dto.crud.UtenteDTO;
import com.demo.service.Utente.UtenteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utenti")
public class UtenteController {
    private final UtenteService utenteService;

    public UtenteController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtenteDTO> getUtente(@PathVariable Long id) {
        return ResponseEntity.ok(utenteService.getUtenteById(id));
    }

    @GetMapping
    public ResponseEntity<List<UtenteDTO>> getAllUtenti() {
        return ResponseEntity.ok(utenteService.getAllUtenti());
    }

    @PostMapping
    public ResponseEntity<UtenteDTO> createUtente(@RequestBody CreaUtenteDTO creaUtenteDTO) {
        return ResponseEntity.ok(utenteService.createUtente(creaUtenteDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtenteDTO> updateUtente(@PathVariable Long id, @RequestBody UpdateUtenteDTO updateUtenteDTO) {
        return ResponseEntity.ok(utenteService.updateUtente(id, updateUtenteDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtente(@PathVariable Long id) {
        utenteService.deleteUtente(id);
        return ResponseEntity.noContent().build();
    }


}