package com.demo.mapper;

import com.demo.object.dto.crud.ProdottoDTO;
import com.demo.object.model.Prodotto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdottoMapper {
    ProdottoDTO toDTO(Prodotto prodotto);
    Prodotto toEntity(ProdottoDTO prodottoDTO);
}
