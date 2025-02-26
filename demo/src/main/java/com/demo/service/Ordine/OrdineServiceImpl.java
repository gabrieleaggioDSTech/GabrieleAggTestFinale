package com.demo.service.Ordine;

import com.demo.object.dto.CreaDettaglioOrdineDTO;
import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.crud.DettaglioOrdineDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import com.demo.object.model.Ordine;
import com.demo.object.model.Prodotto;
import com.demo.object.model.Utente;
import com.demo.repository.OrdineRepository;
import com.demo.repository.ProdottoRepository;
import com.demo.repository.UtenteRepository;
import com.demo.tools.DevTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineServiceImpl implements OrdineService {

    private final OrdineRepository ordineRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;

    public OrdineServiceImpl(OrdineRepository ordineRepository, UtenteRepository utenteRepository, ProdottoRepository prodottoRepository) {
        this.ordineRepository = ordineRepository;
        this.utenteRepository = utenteRepository;
        this.prodottoRepository = prodottoRepository;
    }

    @Override
    public OrdineDTO getOrdineById(Long id) {
        return ordineRepository.findById(id)
                .map(DevTools::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<OrdineDTO> getAllOrdini() {
        return ordineRepository.findAll().stream()
                .map(DevTools::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrdineDTO createOrdine(CreaOrdineDTO creaOrdineDTO) {
        Utente utente = utenteRepository.findById(creaOrdineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null; // Oppure lanciare un'eccezione con ResponseEntity
        }

        // Creazione dell'ordine
        Ordine ordine = new Ordine();
        ordine.setData(creaOrdineDTO.getData());
        ordine.setUtente(utente);

        List<DettaglioOrdine> dettagliOrdine = new ArrayList<>();

        for (CreaDettaglioOrdineDTO dettaglioDTO : creaOrdineDTO.getDettagli()) {
            Prodotto prodotto = prodottoRepository.findById(dettaglioDTO.getProdottoId()).orElse(null);
            if (prodotto == null || prodotto.getPrezzo() == null) {
                continue;
            }

            DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
            dettaglioOrdine.setOrdine(ordine);
            dettaglioOrdine.setProdotto(prodotto);
            dettaglioOrdine.setQuantita(dettaglioDTO.getQuantita());

            double prezzoTotale = prodotto.getPrezzo() * dettaglioDTO.getQuantita();
            dettaglioOrdine.setPrezzoTotale(prezzoTotale);

            dettagliOrdine.add(dettaglioOrdine);
        }

        if (dettagliOrdine.isEmpty()) {
            return null; // Oppure lanciare un'eccezione specifica
        }

        ordine.setDettagli(dettagliOrdine);
        ordine.setTotale(dettagliOrdine.stream().mapToDouble(DettaglioOrdine::getPrezzoTotale).sum());

        Ordine savedOrdine = ordineRepository.save(ordine);
        return DevTools.convertToDTO(savedOrdine);
    }

    @Override
    @Transactional
    public OrdineDTO updateOrdine(Long id, OrdineDTO ordineDTO) {
        return ordineRepository.findById(id)
                .map(existingOrdine -> {
                    existingOrdine.setData(ordineDTO.getData());
                    existingOrdine.setStato(ordineDTO.getStato());
                    existingOrdine.setTotale(ordineDTO.getTotale());
                    return DevTools.convertToDTO(ordineRepository.save(existingOrdine));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteOrdine(Long id) {
        ordineRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrdineDTO saveOrdine(OrdineDTO ordineDTO) {
        Utente utente = utenteRepository.findById(ordineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null;
        }
        Ordine ordine = DevTools.convertToEntity(ordineDTO, utente);
        Ordine savedOrdine = ordineRepository.save(ordine);
        return DevTools.convertToDTO(savedOrdine);
    }
}