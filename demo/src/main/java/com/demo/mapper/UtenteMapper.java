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
     *
     * @param ordini
     * @return
     */
    @Named("mapOrdiniToIds")
    default List<Long> mapOrdiniToIds(List<Ordine> ordini) {
        return ordini != null ? ordini.stream().map(Ordine::getId).collect(Collectors.toList()) : null;
    }



}
