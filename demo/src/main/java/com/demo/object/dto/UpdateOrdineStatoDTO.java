package com.demo.object.dto;

import com.demo.tools.enumarazioni.OrdineStato;
import lombok.Data;

@Data
public class UpdateOrdineStatoDTO {
    private OrdineStato stato;
}
