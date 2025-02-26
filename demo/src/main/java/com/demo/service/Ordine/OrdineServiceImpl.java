package com.demo.service.Ordine;

import com.demo.mapper.OrdineMapper;
import com.demo.object.dto.CreaDettaglioOrdineDTO;
import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.UpdateOrdineStatoDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import com.demo.object.model.Ordine;
import com.demo.object.model.Prodotto;
import com.demo.object.model.Utente;
import com.demo.repository.OrdineRepository;
import com.demo.repository.ProdottoRepository;
import com.demo.repository.UtenteRepository;
import com.demo.tools.enumarazioni.OrdineStato;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineServiceImpl implements OrdineService {

    private final OrdineRepository ordineRepository;
    private final UtenteRepository utenteRepository;
    private final ProdottoRepository prodottoRepository;
    private final OrdineMapper ordineMapper;

    public OrdineServiceImpl(OrdineRepository ordineRepository, UtenteRepository utenteRepository, ProdottoRepository prodottoRepository, OrdineMapper ordineMapper) {
        this.ordineRepository = ordineRepository;
        this.utenteRepository = utenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.ordineMapper  = ordineMapper;
    }

    /**
     * get by id di ordine
     * @param id dell'ordine per get
     * @return ordine
     */
    @Override
    public OrdineDTO getOrdineById(Long id) {
        return ordineRepository.findById(id)
                .map(ordineMapper::toDTO)
                .orElse(null);
    }

    /**
     * get all di ordini
     * @return lista di tutti gli ordini
     */
    @Override
    public List<OrdineDTO> getAllOrdini() {
        return ordineRepository.findAll().stream()
                .map(ordineMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Creazione nuovo ordine, stato di default IN_ATTESA
     * Per ogni dettaglioOrdine recupera prodotto associato, calcola il prezzo totale,
     * crea un nuovo DettaglioOrdine e lo associa al suo ordine, andandolo a salvare su DB.
     * @param creaOrdineDTO DTO contenente le informazioni dell'ordine.
     * @return DTO dell'ordine creato o null se l'utente non esiste o i dettagli sono vuoti.
     */
    @Override
    @Transactional
    public OrdineDTO createOrdine(CreaOrdineDTO creaOrdineDTO) {
        Utente utente = utenteRepository.findById(creaOrdineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null;
        }

        Ordine ordine = ordineMapper.toEntity(creaOrdineDTO);
        ordine.setUtente(utente);
        ordine.setStato(String.valueOf(OrdineStato.IN_ATTESA));

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
            dettaglioOrdine.setPrezzoTotale(prodotto.getPrezzo() * dettaglioDTO.getQuantita());

            dettagliOrdine.add(dettaglioOrdine);
        }

        if (dettagliOrdine.isEmpty()) {
            return null;
        }

        ordine.setDettagli(dettagliOrdine);
        ordine.setTotale(dettagliOrdine.stream().mapToDouble(DettaglioOrdine::getPrezzoTotale).sum());

        Ordine savedOrdine = ordineRepository.save(ordine);

        return ordineMapper.toDTO(savedOrdine);
    }

    /**
     * Aggiorna un ordine esistente mantenendo lo stesso ID.
     * (Valutare opzioni per cancellazione logica di dati dal DB.)
     *
     * @param id ID dell'ordine da aggiornare.
     * @param ordineDTO DTO contenente i nuovi dati dell'ordine.
     * @return DTO dell'ordine aggiornato o null se l'ordine non esiste.
     */
    @Override
    @Transactional
    public OrdineDTO updateOrdine(Long id, OrdineDTO ordineDTO) {
        return ordineRepository.findById(id)
                .map(existingOrdine -> {
                    Ordine updatedOrdine = ordineMapper.toEntity(ordineDTO);
                    updatedOrdine.setId(existingOrdine.getId()); // Mantieni lo stesso ID
                    return ordineMapper.toDTO(ordineRepository.save(updatedOrdine));
                })
                .orElse(null);
    }

    /**
     * delete by id
     * @param id ordine da cancellare
     */
    @Override
    @Transactional
    public void deleteOrdine(Long id) {
        ordineRepository.deleteById(id);
    }

    /**
     * Salva un nuovo ordine associandolo a un utente esistente.
     *
     * @param ordineDTO DTO contenente i dati dell'ordine.
     * @return DTO dell'ordine salvato o null se l'utente non esiste.
     */
    @Override
    @Transactional
    public OrdineDTO saveOrdine(OrdineDTO ordineDTO) {
        Utente utente = utenteRepository.findById(ordineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null;
        }
        Ordine ordine = ordineMapper.toEntity(ordineDTO);
        ordine.setUtente(utente);

        Ordine savedOrdine = ordineRepository.save(ordine);
        return ordineMapper.toDTO(savedOrdine);
    }


    /**
     * Aggiorna lo stato di un ordine esistente.
     *
     * @param id ID dell'ordine da aggiornare.
     * @param updateOrdineStatoDTO DTO contenente il nuovo stato.
     * @return DTO dell'ordine aggiornato o null se non trovato.
     */
    @Override
    @Transactional
    public OrdineDTO updateOrdineStato(Long id, UpdateOrdineStatoDTO updateOrdineStatoDTO) {
        return ordineRepository.findById(id)
                .map(existingOrdine -> {
                    existingOrdine.setStato(updateOrdineStatoDTO.getStato().name());
                    return ordineMapper.toDTO(ordineRepository.save(existingOrdine));
                })
                .orElse(null);
    }



}