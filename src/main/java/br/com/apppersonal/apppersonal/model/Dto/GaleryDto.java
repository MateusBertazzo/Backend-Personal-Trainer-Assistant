package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.UserGaleryEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GaleryDto {
    private Long id;
    private Long userId;
    private String urlFoto;
    private LocalDate dataFoto;
}
