package com.demo.mapper;

import com.demo.object.dto.crud.DettaglioOrdineDTO;
import com.demo.object.model.DettaglioOrdine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DettaglioOrdineMapper {

    @Mapping(source = "ordine.id", target = "ordineId") // Associa l'ID dell'ordine
    @Mapping(source = "prodotto.id", target = "prodottoId") // Associa l'ID del prodotto
    DettaglioOrdineDTO toDTO(DettaglioOrdine dettaglioOrdine);

    @Mapping(source = "ordineId", target = "ordine.id")
    @Mapping(source = "prodottoId", target = "prodotto.id")
    DettaglioOrdine toEntity(DettaglioOrdineDTO dto);
}
