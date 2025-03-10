package com.demo.service.Ordine;

import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.UpdateOrdineStatoDTO;
import com.demo.object.dto.crud.OrdineDTO;

import java.util.List;

public interface OrdineService {
    OrdineDTO getOrdineById(Long id);
    List<OrdineDTO> getAllOrdini();
    OrdineDTO createOrdine(CreaOrdineDTO creaOrdineDTO);
    OrdineDTO updateOrdine(Long id, OrdineDTO ordineDTO);
    void deleteOrdine(Long id);
    OrdineDTO saveOrdine(OrdineDTO ordineDTO);
    OrdineDTO updateOrdineStato(Long id, UpdateOrdineStatoDTO updateOrdineStatoDTO);
}
