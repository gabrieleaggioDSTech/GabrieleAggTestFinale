package com.demo.service;

import com.demo.mapper.UtenteMapper;
import com.demo.object.dto.crud.UtenteDTO;
import com.demo.object.model.Utente;
import com.demo.repository.UtenteRepository;
import com.demo.service.Utente.UtenteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtenteServiceTest {

    @Mock
    private UtenteRepository utenteRepository; //Mock dei repository

    @InjectMocks
    private UtenteServiceImpl utenteService;

    @Mock
    private UtenteMapper utenteMapper;

    private List<Utente> utenti;
    private List<UtenteDTO> utentiDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        utenteService = new UtenteServiceImpl(utenteRepository, utenteMapper);

        utenti = Arrays.asList(
                new Utente(1L, "Gabriele", "g@email.it", List.of()),
                new Utente(2L, "Max", "max@email.it", List.of())
        );

        utentiDTO = Arrays.asList(
                new UtenteDTO(1L, "Gabriele", "g@email.it", List.of()),
                new UtenteDTO(2L, "Max", "max@email.it", List.of())
        );
    }

    @Test
    void testGetUtenteById(){

        // Mock repository
        Utente utente = new Utente(1L, "Mario Rossi", "mario@example.com", new ArrayList<>());
        when(utenteRepository.findById(any(Long.class))).thenReturn(Optional.of(utente));

        // Mock mapper (ora usa any(Utente.class))
        UtenteDTO utenteDTO = new UtenteDTO(1L, "Mario Rossi", "mario@example.com", new ArrayList<>());
        when(utenteMapper.toDTO(any(Utente.class))).thenReturn(utenteDTO);

        // Chiamata al metodo
        UtenteDTO result = utenteService.getUtenteById(1L);

        // Verifica che il repository sia stato chiamato
        verify(utenteRepository, atLeastOnce()).findById(any(Long.class));

        // Verifica che il mapper sia stato chiamato
        verify(utenteMapper, times(1)).toDTO(any(Utente.class));

        // Asserzioni
        assertNotNull(result, "❌ Il risultato non deve essere null");

    }

    @Test
    void testGetAllUtenti() {

        //Arrange
        when(utenteRepository.findAll()).thenReturn(utenti);
        when(utenteMapper.toDTO(utenti.get(0))).thenReturn(utentiDTO.get(0));
        when(utenteMapper.toDTO(utenti.get(1))).thenReturn(utentiDTO.get(1));

        //Act
        List<UtenteDTO> risultato = utenteService.getAllUtenti();

        //Assert
        assertNotNull(risultato);
        assertEquals(2, risultato.size());
        assertEquals("Gabriele", risultato.get(0).getNome());
        assertEquals("Max", risultato.get(1).getNome());

        //Verifica che il repo sia chiamato una volta.
        verify(utenteRepository, times(1)).findAll();
        //Verifica del mapper che stia vendendo chiamato per ogni utente
        verify(utenteMapper, times(1)).toDTO(utenti.get(0));
        verify(utenteMapper, times(1)).toDTO(utenti.get(1));


    }



}
