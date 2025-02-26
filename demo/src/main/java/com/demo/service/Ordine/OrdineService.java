package com.demo.service.Ordine;

import com.demo.object.dto.OrdineDTO;

import java.util.List;

public interface OrdineService {
    OrdineDTO getOrdineById(Long id);
    List<OrdineDTO> getAllOrdini();
    OrdineDTO createOrdine(OrdineDTO ordineDTO);
    OrdineDTO updateOrdine(Long id, OrdineDTO ordineDTO);
    void deleteOrdine(Long id);
    OrdineDTO saveOrdine(OrdineDTO ordineDTO);
}
