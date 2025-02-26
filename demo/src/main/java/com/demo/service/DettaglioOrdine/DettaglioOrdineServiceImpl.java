package com.demo.service.DettaglioOrdine;


import com.demo.object.dto.crud.DettaglioOrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import com.demo.object.model.Ordine;
import com.demo.object.model.Prodotto;
import com.demo.repository.DettaglioOrdineRepository;
import com.demo.repository.OrdineRepository;
import com.demo.repository.ProdottoRepository;
import com.demo.tools.DevTools;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DettaglioOrdineServiceImpl implements DettaglioOrdineService {

    private final DettaglioOrdineRepository dettaglioOrdineRepository;
    private final OrdineRepository ordineRepository;
    private final ProdottoRepository prodottoRepository;

    public DettaglioOrdineServiceImpl(DettaglioOrdineRepository dettaglioOrdineRepository, OrdineRepository ordineRepository, ProdottoRepository prodottoRepository) {
        this.dettaglioOrdineRepository = dettaglioOrdineRepository;
        this.ordineRepository = ordineRepository;
        this.prodottoRepository = prodottoRepository;
    }

    @Override
    public DettaglioOrdineDTO getDettaglioOrdineById(Long id) {
        return dettaglioOrdineRepository.findById(id)
                .map(DevTools::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<DettaglioOrdineDTO> getAllDettagliOrdini() {
        return dettaglioOrdineRepository.findAll().stream()
                .map(DevTools::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DettaglioOrdineDTO createDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO) {
        Ordine ordine = ordineRepository.findById(dettaglioOrdineDTO.getOrdineId()).orElse(null);
        Prodotto prodotto = prodottoRepository.findById(dettaglioOrdineDTO.getProdottoId()).orElse(null);
        if (ordine == null || prodotto == null) {
            return null;
        }
        DettaglioOrdine dettaglioOrdine = DevTools.convertToEntity(dettaglioOrdineDTO, ordine, prodotto);
        DettaglioOrdine savedDettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);
        return DevTools.convertToDTO(savedDettaglioOrdine);
    }

    @Override
    @Transactional
    public DettaglioOrdineDTO updateDettaglioOrdine(Long id, DettaglioOrdineDTO dettaglioOrdineDTO) {
        return dettaglioOrdineRepository.findById(id)
                .map(existingDettaglio -> {
                    existingDettaglio.setQuantita(dettaglioOrdineDTO.getQuantita());
                    existingDettaglio.setPrezzoTotale(dettaglioOrdineDTO.getPrezzoTotale());
                    return DevTools.convertToDTO(dettaglioOrdineRepository.save(existingDettaglio));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteDettaglioOrdine(Long id) {
        dettaglioOrdineRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DettaglioOrdineDTO saveDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO) {
        Ordine ordine = ordineRepository.findById(dettaglioOrdineDTO.getOrdineId()).orElse(null);
        Prodotto prodotto = prodottoRepository.findById(dettaglioOrdineDTO.getProdottoId()).orElse(null);
        if (ordine == null || prodotto == null) {
            return null;
        }
        DettaglioOrdine dettaglioOrdine = DevTools.convertToEntity(dettaglioOrdineDTO, ordine, prodotto);
        DettaglioOrdine savedDettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);
        return DevTools.convertToDTO(savedDettaglioOrdine);
    }
}