package com.demo.service.Utente;

import com.demo.mapper.UtenteMapper;
import com.demo.object.dto.CreaUtenteDTO;
import com.demo.object.dto.UpdateUtenteDTO;
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
    private final UtenteMapper utenteMapper;

    public UtenteServiceImpl(UtenteRepository utenteRepository, UtenteMapper utenteMapper){
        this.utenteRepository = utenteRepository;
        this.utenteMapper = utenteMapper;
    }

    @Override
    public UtenteDTO getUtenteById(Long id) {
        return utenteRepository.findById(id)
                .map(utenteMapper::toDTO)
                .orElse(null);
    }

    @Override
    public List<UtenteDTO> getAllUtenti() {
        return utenteRepository.findAll().stream()
                .map(utenteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UtenteDTO createUtente(CreaUtenteDTO creaUtenteDTO) {
        Utente utente = new Utente();
        utente.setNome(creaUtenteDTO.getNome());
        utente.setEmail(creaUtenteDTO.getEmail());
        Utente savedUtente = utenteRepository.save(utente);
        return utenteMapper.toDTO(savedUtente);
    }

    @Override
    @Transactional
    public UtenteDTO updateUtente(Long id, UpdateUtenteDTO updateUtenteDTO) {
        return utenteRepository.findById(id)
                .map(existingUtente -> {
                    existingUtente.setNome(updateUtenteDTO.getNome());
                    existingUtente.setEmail(updateUtenteDTO.getEmail());
                    return utenteMapper.toDTO(utenteRepository.save(existingUtente));
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
        Utente utente = utenteMapper.toEntity(user);
        Utente savedUtente = utenteRepository.save(utente);
        return utenteMapper.toDTO(savedUtente);
    }

}

