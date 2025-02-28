package com.demo.service.OrdineAnalisi;

import com.demo.mapper.OrdineMapper;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.model.Ordine;
import com.demo.repository.OrdineRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineAnalisiServiceImpl implements OrdineAnalisiService{

    private final OrdineRepository ordineRepository;
    private final OrdineMapper ordineMapper;

    public OrdineAnalisiServiceImpl(OrdineRepository ordineRepository, OrdineMapper ordineMapper) {
        this.ordineRepository = ordineRepository;
        this.ordineMapper = ordineMapper;
    }

    /**
     * Calcola il totale speso da un utente.
     *
     * @param userId ID dell'utente.
     * @return Totale speso dall'utente o 0 se non ha ordini.
     */
    @Override
    public Double getTotaleSpeso(Long userId) {
        return ordineRepository.getTotaleSpesoByUtente(userId);
    }

    /**
     * Recupera tutti gli ordini effettuati in un intervallo di date.
     *
     * @param startDate Data di inizio intervallo.
     * @param endDate   Data di fine intervallo.
     * @return Lista di ordini nell'intervallo specificato.
     */
    @Override
    public List<OrdineDTO> getOrdiniByIntervallo(LocalDate startDate, LocalDate endDate) {
        List<Ordine> ordini = ordineRepository.findOrdiniByIntervallo(startDate, endDate);
        return ordini.stream()
                .map(ordineMapper::toDTO)
                .collect(Collectors.toList());
    }
}
