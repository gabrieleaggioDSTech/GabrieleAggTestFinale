package com.demo.service.Utente;

import com.demo.object.dto.CreaUtenteDTO;
import com.demo.object.dto.UpdateUtenteDTO;
import com.demo.object.dto.crud.UtenteDTO;

import java.util.List;

public interface UtenteService {
    UtenteDTO getUtenteById(Long id);
    List<UtenteDTO> getAllUtenti();
    UtenteDTO createUtente(CreaUtenteDTO creaUtenteDTO);
    UtenteDTO updateUtente(Long id, UpdateUtenteDTO updateUtenteDTO);
    void deleteUtente(Long id);
    UtenteDTO saveUser(UtenteDTO user);
}
