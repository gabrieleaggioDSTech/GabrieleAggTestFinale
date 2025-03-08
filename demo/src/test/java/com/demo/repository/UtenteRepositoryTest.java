package com.demo.repository;

import com.demo.object.model.Utente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*; 
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UtenteRepositoryTest {

    @Mock
    private UtenteRepository utenteRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUtenteById(){

        //Arrange / preparazione
        Utente utente = new Utente(1L, "Mario", "testt@test.it", new ArrayList<>());
        when(utenteRepository.findById(anyLong())).thenReturn(Optional.of(utente));

        //Act / azione
        Optional<Utente> result = utenteRepository.findById(1L);

        //Assert / Verifica
        Assertions.assertEquals(utente, result.orElse(null));
        assertNotNull(utente);
        assertNotNull(result);
    }

    @Test
    void testGetAllUtenti(){

        //Arrange preparazione
        List<Utente> utenteList = new ArrayList<>(Arrays.asList(
                new Utente(1L, "Gabriele", "g@email.it", List.of()),
                new Utente(2L, "Max", "max@email.it", List.of())
        ));

        when(utenteRepository.findAll()).thenReturn(utenteList);

        //Act Azione
        List<Utente> risultato = utenteRepository.findAll();

        //Assert Verifica
        assertEquals(risultato, utenteList);
        assertNotNull(risultato);
        verify(utenteRepository, times(1)).findAll();
        assertEquals(2, risultato.size());

    }
}
