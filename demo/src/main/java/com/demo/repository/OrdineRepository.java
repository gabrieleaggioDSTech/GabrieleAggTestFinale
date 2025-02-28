package com.demo.repository;

import com.demo.object.model.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrdineRepository extends JpaRepository<Ordine, Long> {

    /**
     * Calcola il totale speso da utente sommando tutti gli ordini.
     * Se l'utente non ha ordini return 0.
     * Query JPQL a seguire.
     *
     * @param userId Id dell'utente per cui calcolare il totale speso.
     * @return Totale speso dall'utente o 0 se non ha ordini
     */
    @Query("SELECT COALESCE(SUM(o.totale), 0) FROM Ordine o WHERE o.utente.id = :userId")
    Double getTotaleSpesoByUtente(@Param("userId") Long userId);

    /**
     * Recupera tutti gli ordini effettuati in un intervallo di tempo.
     *
     * @param startDate Data di inizio.
     * @param endDate   Data di fine.
     * @return Lista di ordini effettuati nel periodo specificato.
     */
    @Query("SELECT o FROM Ordine o WHERE o.data BETWEEN :startDate AND :endDate")
    List<Ordine> findOrdiniByIntervallo(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
