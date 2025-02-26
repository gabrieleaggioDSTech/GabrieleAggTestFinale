package com.demo.service.DettaglioOrdine;


import com.demo.mapper.DettaglioOrdineMapper;
import com.demo.object.dto.crud.DettaglioOrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import com.demo.object.model.Ordine;
import com.demo.object.model.Prodotto;
import com.demo.repository.DettaglioOrdineRepository;
import com.demo.repository.OrdineRepository;
import com.demo.repository.ProdottoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DettaglioOrdineServiceImpl implements DettaglioOrdineService {

    private final DettaglioOrdineRepository dettaglioOrdineRepository;
    private final OrdineRepository ordineRepository;
    private final ProdottoRepository prodottoRepository;
    private final DettaglioOrdineMapper dettaglioOrdineMapper;

    public DettaglioOrdineServiceImpl(DettaglioOrdineRepository dettaglioOrdineRepository, OrdineRepository ordineRepository, ProdottoRepository prodottoRepository, DettaglioOrdineMapper dettaglioOrdineMapper) {
        this.dettaglioOrdineRepository = dettaglioOrdineRepository;
        this.ordineRepository = ordineRepository;
        this.prodottoRepository = prodottoRepository;
        this.dettaglioOrdineMapper = dettaglioOrdineMapper;
    }

    /**
     * get by id del dettaglio ordine
     * @param id per get
     * @return dettaglio ordine
     */
    @Override
    public DettaglioOrdineDTO getDettaglioOrdineById(Long id) {
        return dettaglioOrdineRepository.findById(id)
                .map(dettaglioOrdineMapper::toDTO)
                .orElse(null);
    }

    /**
     * Get all dei dettagli ordine
     * @return lista di tutti i dettagli ordine
     */
    @Override
    public List<DettaglioOrdineDTO> getAllDettagliOrdini() {
        return dettaglioOrdineRepository.findAll().stream()
                .map(dettaglioOrdineMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuovo DettaglioOrdine dopo aver validato l'esistenza dell'ordine e del prodotto.
     *
     * @param dettaglioOrdineDTO DTO contenente i dati del nuovo dettaglio ordine.
     * @return DettaglioOrdineDTO creato o null se l'ordine o il prodotto non esistono.
     */
    @Override
    @Transactional
    public DettaglioOrdineDTO createDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO) {
        Ordine ordine = ordineRepository.findById(dettaglioOrdineDTO.getOrdineId()).orElse(null);
        Prodotto prodotto = prodottoRepository.findById(dettaglioOrdineDTO.getProdottoId()).orElse(null);
        if (ordine == null || prodotto == null) {
            return null;
        }
        DettaglioOrdine dettaglioOrdine = dettaglioOrdineMapper.toEntity(dettaglioOrdineDTO);
        dettaglioOrdine.setOrdine(ordine);
        dettaglioOrdine.setProdotto(prodotto);

        DettaglioOrdine savedDettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);
        return dettaglioOrdineMapper.toDTO(savedDettaglioOrdine);
    }

    /**
     * Aggiorna un DettaglioOrdine esistente con i nuovi valori forniti.
     *
     * @param id Identificativo del DettaglioOrdine da aggiornare.
     * @param dettaglioOrdineDTO DTO contenente i nuovi valori per il dettaglio ordine.
     * @return DettaglioOrdineDTO aggiornato o null se il dettaglio ordine non esiste.
     */
    @Override
    @Transactional
    public DettaglioOrdineDTO updateDettaglioOrdine(Long id, DettaglioOrdineDTO dettaglioOrdineDTO) {
        return dettaglioOrdineRepository.findById(id)
                .map(existingDettaglio -> {
                    existingDettaglio.setQuantita(dettaglioOrdineDTO.getQuantita());
                    existingDettaglio.setPrezzoTotale(dettaglioOrdineDTO.getPrezzoTotale());
                    return dettaglioOrdineMapper.toDTO(dettaglioOrdineRepository.save(existingDettaglio));
                })
                .orElse(null);
    }

    /**
     * Cancellazione ordine fisica dal db.
     *
     * @param id dell'ordine da cancellare
     */
    @Override
    @Transactional
    public void deleteDettaglioOrdine(Long id) {
        dettaglioOrdineRepository.deleteById(id);
    }

    /**
     * Salva un nuovo DettaglioOrdine dopo aver validato l'ordine e il prodotto.
     * (Vedere metodo accessorio -> DettaglioOrdine validaECreaDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO)
     *
     * @param dettaglioOrdineDTO DTO contenente i dati del dettaglio ordine.
     * @return DettaglioOrdineDTO salvato o null se l'ordine o il prodotto non esistono.
     */
    @Override
    @Transactional
    public DettaglioOrdineDTO saveDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO) {
        DettaglioOrdine dettaglioOrdine = validaECreaDettaglioOrdine(dettaglioOrdineDTO);
        if (dettaglioOrdine == null) {
            return null;
        }
        DettaglioOrdine savedDettaglioOrdine = dettaglioOrdineRepository.save(dettaglioOrdine);
        return dettaglioOrdineMapper.toDTO(savedDettaglioOrdine);
    }


    /**
     * Valida l'esistenza di un ordine e un prodotto e crea un DettaglioOrdine.
     * Metodo accessorio usato in saveDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO);
     *
     * @param dettaglioOrdineDTO DTO contenente i dati per il dettaglio ordine.
     * @return Un DettaglioOrdine pronto per essere salvato o null se l'ordine o il prodotto non esistono.
     */
    private DettaglioOrdine validaECreaDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO) {
        Ordine ordine = ordineRepository.findById(dettaglioOrdineDTO.getOrdineId()).orElse(null);
        Prodotto prodotto = prodottoRepository.findById(dettaglioOrdineDTO.getProdottoId()).orElse(null);
        if (ordine == null || prodotto == null) {
            return null;
        }
        DettaglioOrdine dettaglioOrdine = dettaglioOrdineMapper.toEntity(dettaglioOrdineDTO);
        dettaglioOrdine.setOrdine(ordine);
        dettaglioOrdine.setProdotto(prodotto);
        return dettaglioOrdine;
    }


}