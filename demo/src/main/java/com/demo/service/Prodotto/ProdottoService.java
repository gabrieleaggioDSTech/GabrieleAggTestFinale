package com.demo.service.Prodotto;

import com.demo.object.dto.ProdottoDTO;

import java.util.List;

public interface ProdottoService {
    ProdottoDTO getProdottoById(Long id);
    List<ProdottoDTO> getAllProdotti();
    ProdottoDTO createProdotto(ProdottoDTO prodottoDTO);
    ProdottoDTO updateProdotto(Long id, ProdottoDTO prodottoDTO);
    void deleteProdotto(Long id);
    ProdottoDTO saveProdotto(ProdottoDTO prodottoDTO);
}
