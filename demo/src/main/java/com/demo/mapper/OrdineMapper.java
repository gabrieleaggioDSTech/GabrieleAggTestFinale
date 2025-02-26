package com.demo.mapper;

import com.demo.object.dto.CreaOrdineDTO;
import com.demo.object.dto.crud.OrdineDTO;
import com.demo.object.model.Ordine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DettaglioOrdineMapper.class)
public interface OrdineMapper {
    @Mapping(source = "utente.id", target = "utenteId")
    OrdineDTO toDTO(Ordine ordine);

    @Mapping(source = "utenteId", target = "utente.id")
    Ordine toEntity(OrdineDTO dto);

    Ordine toEntity(CreaOrdineDTO creaOrdineDTO);


}
