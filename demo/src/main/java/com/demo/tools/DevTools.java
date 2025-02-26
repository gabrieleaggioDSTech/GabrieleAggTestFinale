package com.demo.tools;

import com.demo.object.dto.crud.DettaglioOrdineDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.dto.crud.ProdottoDTO;
import com.demo.object.dto.crud.UtenteDTO;
import com.demo.object.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class DevTools {

    public static UtenteDTO convertToDTO(Utente utente) {
        if (utente == null) {
            return null;
        }

        List<Long> ordineIds;
        if (utente.getOrdini() == null || utente.getOrdini().isEmpty()) {
            ordineIds = List.of();
        } else {
            ordineIds = utente.getOrdini().stream()
                    .map(Ordine::getId)
                    .collect(Collectors.toList());
        }
        return new UtenteDTO(utente.getId(), utente.getNome(), utente.getEmail(), ordineIds);
    }

    public static Utente convertToEntity(UtenteDTO utenteDTO) {
        if (utenteDTO == null) {
            return null;
        }
        Utente utente = new Utente();
        utente.setId(utenteDTO.getId());
        utente.setNome(utenteDTO.getNome());
        utente.setEmail(utenteDTO.getEmail());
        return utente;
    }

    public static OrdineDTO convertToDTO(Ordine ordine) {
        if (ordine == null) {
            return null;
        }
        return new OrdineDTO(ordine.getId(), ordine.getData(), ordine.getStato(), ordine.getTotale(), ordine.getUtente().getId(), null);
    }

    public static Ordine convertToEntity(OrdineDTO ordineDTO, Utente utente) {
        if (ordineDTO == null) {
            return null;
        }
        Ordine ordine = new Ordine();
        ordine.setId(ordineDTO.getId());
        ordine.setData(ordineDTO.getData());
        ordine.setStato(ordineDTO.getStato());
        ordine.setTotale(ordineDTO.getTotale());
        ordine.setUtente(utente);
        return ordine;
    }

    public static ProdottoDTO convertToDTO(Prodotto prodotto) {
        if (prodotto == null) {
            return null;
        }
        return new ProdottoDTO(prodotto.getId(), prodotto.getNome(), prodotto.getPrezzo());
    }

    public static Prodotto convertToEntity(ProdottoDTO prodottoDTO) {
        if (prodottoDTO == null) {
            return null;
        }
        Prodotto prodotto = new Prodotto();
        prodotto.setId(prodottoDTO.getId());
        prodotto.setNome(prodottoDTO.getNome());
        prodotto.setPrezzo(prodottoDTO.getPrezzo());
        return prodotto;
    }

    public static DettaglioOrdineDTO convertToDTO(DettaglioOrdine dettaglioOrdine) {
        if (dettaglioOrdine == null) {
            return null;
        }
        return new DettaglioOrdineDTO(dettaglioOrdine.getId(), dettaglioOrdine.getQuantita(), dettaglioOrdine.getPrezzoTotale(),
                dettaglioOrdine.getOrdine().getId(), dettaglioOrdine.getProdotto().getId());
    }

    public static DettaglioOrdine convertToEntity(DettaglioOrdineDTO dettaglioOrdineDTO, Ordine ordine, Prodotto prodotto) {
        if (dettaglioOrdineDTO == null) {
            return null;
        }
        DettaglioOrdine dettaglioOrdine = new DettaglioOrdine();
        dettaglioOrdine.setId(dettaglioOrdineDTO.getId());
        dettaglioOrdine.setQuantita(dettaglioOrdineDTO.getQuantita());
        dettaglioOrdine.setPrezzoTotale(dettaglioOrdineDTO.getPrezzoTotale());
        dettaglioOrdine.setOrdine(ordine);
        dettaglioOrdine.setProdotto(prodotto);
        return dettaglioOrdine;
    }
}
