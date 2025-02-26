package com.demo.service.Ordine;

import com.demo.object.dto.OrdineDTO;
import com.demo.object.model.Ordine;
import com.demo.object.model.Utente;
import com.demo.repository.OrdineRepository;
import com.demo.repository.UtenteRepository;
import com.demo.tools.DevTools;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdineServiceImpl implements OrdineService {

    private final OrdineRepository ordineRepository;
    private final UtenteRepository utenteRepository;

    public OrdineServiceImpl(OrdineRepository ordineRepository, UtenteRepository utenteRepository) {
        this.ordineRepository = ordineRepository;
        this.utenteRepository = utenteRepository;
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
    public OrdineDTO createOrdine(OrdineDTO ordineDTO) {
        Utente utente = utenteRepository.findById(ordineDTO.getUtenteId()).orElse(null);
        if (utente == null) {
            return null;
        }
        Ordine ordine = DevTools.convertToEntity(ordineDTO, utente);
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