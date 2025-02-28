package com.demo.controller;

import com.demo.object.dto.crud.OrdineDTO;
import com.demo.service.OrdineAnalisi.OrdineAnalisiService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ordini")
public class OrdineAnalisiController {

    private final OrdineAnalisiService ordineAnalisiService;

    public OrdineAnalisiController(OrdineAnalisiService ordineAnalisiService) {
        this.ordineAnalisiService = ordineAnalisiService;
    }

    /**
     * Endpoint per ottenere il totale speso da un utente.
     * Restituisce 404 se l'utente non esiste o non ha ordini.
     *
     * @param userId ID dell'utente.
     * @return Totale speso o 404 se l'utente non ha ordini.
     */
    @GetMapping("/totale-speso/{userId}")
    public ResponseEntity<Double> getTotaleSpeso(@PathVariable Long userId) {
        Double totaleSpeso = ordineAnalisiService.getTotaleSpeso(userId);

        if (totaleSpeso == null || totaleSpeso == 0.0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(totaleSpeso);
    }

    //TODO Per testing usare febbraio start: 2024-02-01 - end: 2024-02-28
    /**
     * Endpoint per ottenere tutti gli ordini in un intervallo di date.
     * Fa una verifica che startDate sia precedente o uguale a endDate.
     *
     * @param startDate Data di inizio.
     * @param endDate   Data di fine.
     * @return List di ordini o errore 400 se i parametri non sono validi.
     */
    @GetMapping("/intervallo")
    public ResponseEntity<List<OrdineDTO>> getOrdiniByIntervallo(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().body(null);
        }
        List<OrdineDTO> ordini = ordineAnalisiService.getOrdiniByIntervallo(startDate, endDate);

        return ResponseEntity.ok(ordini);
    }
}
