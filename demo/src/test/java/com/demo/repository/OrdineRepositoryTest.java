package com.demo.repository;

import com.demo.object.model.Ordine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrdineRepositoryTest {

    @Mock
    OrdineRepository ordineRepository;

    @BeforeEach
    void setUp(){
        //MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIntervalloDiDate(){

        // Arrange: Creiamo ordini simulati con date specifiche
        List<Ordine> ordiniSimulati = Arrays.asList(
                new Ordine(1L, LocalDate.of(2024, 1, 5), "IN_ATTESA", 100.0, null, null),
                new Ordine(2L, LocalDate.of(2024, 1, 20), "SPEDITO", 200.0, null, null)
        );

        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 1, 30);

        //Simuliamo il comportamento del repository
        when(ordineRepository.findOrdiniByIntervallo(startDate, endDate)).thenReturn(ordiniSimulati);

        //ACT chiamiamo il metodo del repository
        List<Ordine> ordiniTraDate = ordineRepository.findOrdiniByIntervallo(startDate, endDate);

        //Assert:
        assertEquals(2, ordiniTraDate.size());
        assertEquals(ordiniSimulati, ordiniTraDate, "La lista restituita deve essere la stessa della simulazione");
        assertEquals(LocalDate.of(2024, 1, 5), ordiniTraDate.get(0).getData(), "La data del primo ordine è errata");
        assertEquals(LocalDate.of(2024, 1, 20), ordiniTraDate.get(1).getData(), "La data del secondo ordine è errata");

    }

    @Test
    void testIntervalloDiDate_NessunOrdine() {

        //Con il when simuliamo il risultato del repository
        when(ordineRepository.findOrdiniByIntervallo(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 2, 28)))
                .thenReturn(List.of());

        List<Ordine> ordiniTraDate = ordineRepository.findOrdiniByIntervallo(
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 28)
        );

        assertEquals(0, ordiniTraDate.size(), "La lista dovrebbe essere vuota se non ci sono ordini nel range");
    }


}
