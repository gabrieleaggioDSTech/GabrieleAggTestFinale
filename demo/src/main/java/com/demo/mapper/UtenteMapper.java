package com.demo.mapper;

import com.demo.object.dto.crud.UtenteDTO;
import com.demo.object.model.Ordine;
import com.demo.object.model.Utente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UtenteMapper {

    @Mapping(source = "ordini", target = "ordiniId", qualifiedByName = "mapOrdiniToIds")
    UtenteDTO toDTO(Utente utente);

    @Mapping(target = "ordini", ignore = true) // Non carichiamo gli ordini automaticamente
    Utente toEntity(UtenteDTO dto);

    /**
     * Metodo che converte la lista completa degli ordini in una lista di ID semplici
     * usato nel mapper per evitare di caricare tutti i campi degli ordini per ogni utente.
     * Il default viene usato per inserire logica direttamente dentro il mapper,
     * senza usare classi esterne. (caso di utilizzo unico del metodo)
     *
     * @param ordini lista di entità Ordine
     * @return lista degli ID degli ordini oppure null se non ci sono ordine associati all'utente.
     */
    @Named("mapOrdiniToIds")
    default List<Long> mapOrdiniToIds(List<Ordine> ordini) {
        return ordini != null ? ordini.stream().map(Ordine::getId).collect(Collectors.toList()) : null;
    }

}
