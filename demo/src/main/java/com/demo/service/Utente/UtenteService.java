package com.demo.service.Utente;

import com.demo.object.dto.CreaUtenteDTO;
import com.demo.object.dto.UtenteDTO;

import java.util.List;

public interface UtenteService {
    UtenteDTO getUtenteById(Long id);
    List<UtenteDTO> getAllUtenti();
    UtenteDTO createUtente(CreaUtenteDTO creaUtenteDTO);
    UtenteDTO updateUtente(Long id, UtenteDTO utenteDTO);
    void deleteUtente(Long id);
    UtenteDTO saveUser(UtenteDTO user);
}
