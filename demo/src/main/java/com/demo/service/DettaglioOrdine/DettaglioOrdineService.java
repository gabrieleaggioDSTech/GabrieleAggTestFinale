package com.demo.service.DettaglioOrdine;

import com.demo.object.dto.DettaglioOrdineDTO;

import java.util.List;

public interface DettaglioOrdineService {
    DettaglioOrdineDTO getDettaglioOrdineById(Long id);
    List<DettaglioOrdineDTO> getAllDettagliOrdini();
    DettaglioOrdineDTO createDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO);
    DettaglioOrdineDTO updateDettaglioOrdine(Long id, DettaglioOrdineDTO dettaglioOrdineDTO);
    void deleteDettaglioOrdine(Long id);
    DettaglioOrdineDTO saveDettaglioOrdine(DettaglioOrdineDTO dettaglioOrdineDTO);
}
