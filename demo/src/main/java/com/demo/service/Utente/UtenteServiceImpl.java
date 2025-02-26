package com.demo.service.Utente;

import com.demo.object.dto.CreaUtenteDTO;
import com.demo.object.dto.crud.UtenteDTO;
import com.demo.object.model.Utente;
import com.demo.repository.UtenteRepository;
import com.demo.tools.DevTools;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    public UtenteServiceImpl(UtenteRepository utenteRepository){
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UtenteDTO getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .map(DevTools::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<UtenteDTO> getAllUtenti() {
        return utenteRepository.findAll().stream()
                .map(DevTools::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UtenteDTO createUtente(CreaUtenteDTO creaUtenteDTO) {
        Utente utente = new Utente();
        utente.setNome(creaUtenteDTO.getNome());
        utente.setEmail(creaUtenteDTO.getEmail());
        Utente savedUtente = utenteRepository.save(utente);
        return DevTools.convertToDTO(savedUtente);
    }

    @Override
    @Transactional
    public UtenteDTO updateUtente(Long id, UtenteDTO utenteDTO) {
        return utenteRepository.findById(id)
                .map(existingUtente -> {
                    existingUtente.setNome(utenteDTO.getNome());
                    existingUtente.setEmail(utenteDTO.getEmail());
                    return DevTools.convertToDTO(utenteRepository.save(existingUtente));
                })
                .orElse(null);
    }

    @Override
    @Transactional
    public void deleteUtente(Long id) {
        utenteRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UtenteDTO saveUser(UtenteDTO user) {
        Utente utente = DevTools.convertToEntity(user);
        Utente savedUtente = utenteRepository.save(utente);
        return DevTools.convertToDTO(savedUtente);
    }

}

