package com.demo.service.OrdineAnalisi;

import com.demo.object.dto.crud.OrdineDTO;

import java.time.LocalDate;
import java.util.List;

public interface OrdineAnalisiService {

    Double getTotaleSpeso(Long userId);

    List<OrdineDTO> getOrdiniByIntervallo(LocalDate startDate, LocalDate endDate);
}
