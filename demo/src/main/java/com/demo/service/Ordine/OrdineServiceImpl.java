package com.demo.service.Ordine;

import com.demo.mapper.OrdineMapper;
import com.demo.object.dto.CreaDettaglioOrdineDTO;
import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import com.demo.object.model.Ordine;
import com.demo.object.model.Prodotto;
import com.demo.object.model.Utente;
import com.demo.repository.OrdineRepository;
import com.demo.repository.ProdottoRepository;
import com.demo.repository.UtenteRepository;
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
    private final OrdineMapper ordineMapper;

    public OrdineServiceImpl(OrdineRepository ordineRepository, UtenteRepository utenteRepository, ProdottoRepository prodottoRepository, OrdineMapper ordineMapper) {
        this.ordineRepository = ordineRepository;
        this.utenteRepository = utenteRepository;
        this.prodottoRepository = prodottoRepository;
        this.ordineMapper  = ordineMapper;
    }

    @Override
    public OrdineDTO getOrdineById(Long id) {
        return ordineRepository.findById(id)
                .map(ordineMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<OrdineDTO> getAllOrdini() {
        return ordineRepository.findAll().stream()
                .map(ordineMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrdineDTO createOrdine(CreaOrdineDTO creaOrdineDTO) {
        Utente utente = utenteRepository.findById(creaOrdineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null;
        }

        Ordine ordine = ordineMapper.toEntity(creaOrdineDTO);
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
        Ordine ordine = ordineMapper.toEntity(ordineDTO);
        ordine.setUtente(utente);

        Ordine savedOrdine = ordineRepository.save(ordine);
        return ordineMapper.toDTO(savedOrdine);
    }
}