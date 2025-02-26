package com.demo.object.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtenteDTO {
    private Long id;
    private String nome;
    private String email;
    private List<Long> ordiniId;
}
