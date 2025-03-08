package com.demo.repository;

import com.demo.object.model.Prodotto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ProdottoRepositoryTest {

    @Mock
    ProdottoRepository prodottoRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProdottoById(){

        //Arrange / preparazione
        Prodotto prodotto = new Prodotto(1L, "nomeProdotto", 12.11);
        when(prodottoRepository.findById(anyLong())).thenReturn(Optional.of(prodotto));

        //Act Azione
        Optional<Prodotto> risultato = prodottoRepository.findById(1L);

        //Assert - Verifica
        Assertions.assertEquals(prodotto, risultato.orElse(null));
        Assertions.assertEquals(prodotto.getId(), risultato.get().getId());
        Assertions.assertEquals(prodotto.getNome(), risultato.get().getNome());
        Assertions.assertNotNull(risultato.get(), "Risultato non deve essere nullo");
    }

    @Test
    void testGetProdottoById_VerifyCall() {
        // Arrange
        Prodotto prodotto = new Prodotto(1L, "nomeProdotto", 12.11);
        when(prodottoRepository.findById(anyLong())).thenReturn(Optional.of(prodotto));

        // Act
        Optional<Prodotto> risultato = prodottoRepository.findById(1L);

        // Assert
        Assertions.assertTrue(risultato.isPresent());

        // Verifica che il metodo sia stato chiamato solo UNA volta
        Mockito.verify(prodottoRepository, Mockito.times(1)).findById(1L);
    }



    @Test
    void testGetAllProdotti(){

        //Arrange / preparazione lista mock di prodotti
        List<Prodotto> prodotti = Arrays.asList(
          new Prodotto(1L, "TV", 400.99),
          new Prodotto(2L, "Laptop", 600.99)
        );


        when(prodottoRepository.findAll()).thenReturn(prodotti);

        //Act / azione chiamiamo il metodo del repository
        List<Prodotto> risultato = prodottoRepository.findAll();

        //Assert - verifica
        Assertions.assertFalse(risultato.isEmpty(), "Lista dei prodotti non deve essere vuota");
        Assertions.assertEquals(2, risultato.size(), "La lista dei prodotti dovrebbe essere di size 2");
        Assertions.assertEquals(prodotti, risultato, "Devono combaciare le liste");
    }


}
