package br.com.apppersonal.apppersonal.model.Dto;

import br.com.apppersonal.apppersonal.model.entitys.UserEntity;
import br.com.apppersonal.apppersonal.security.Role;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserMetricsDto {
    private Long id;
    private Long userId;

    private Role role;

    private LocalDate dataStart;
    
    private Double weight;
    
    private Double height;
    
    private Integer age;
    
    private Double tronco;
    
    private Double quadril;
    
    private Double bracoEsquerdo;
    
    private Double bracoDireito;
    
    private Double pernaEsquerda;
    
    private Double pernaDireita;
    
    private Double panturrilhaEsquerda;
    
    private Double panturrilhaDireita;

    public UserMetricsDto(Long id,
                          Long userId, Role role,
                          LocalDate dataStart,
                          Double weight, Double height,
                          Integer age, Double tronco,
                          Double quadril, Double bracoEsquerdo,
                          Double bracoDireito, Double pernaEsquerda,
                          Double pernaDireita, Double panturrilhaEsquerda, Double panturrilhaDireita) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.dataStart = dataStart;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.tronco = tronco;
        this.quadril = quadril;
        this.bracoEsquerdo = bracoEsquerdo;
        this.bracoDireito = bracoDireito;
        this.pernaEsquerda = pernaEsquerda;
        this.pernaDireita = pernaDireita;
        this.panturrilhaEsquerda = panturrilhaEsquerda;
        this.panturrilhaDireita = panturrilhaDireita;
    }
}
