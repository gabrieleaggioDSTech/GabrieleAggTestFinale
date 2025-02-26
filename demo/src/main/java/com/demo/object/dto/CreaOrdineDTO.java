package com.demo.object.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreaOrdineDTO {
    private LocalDate data;
    private Long utenteId;
    private List<CreaDettaglioOrdineDTO> dettagli;
}
