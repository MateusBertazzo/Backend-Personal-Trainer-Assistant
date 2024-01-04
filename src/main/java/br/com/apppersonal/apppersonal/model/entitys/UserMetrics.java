package br.com.apppersonal.apppersonal.model.entitys;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_metrics")
public class UserMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "data_start")
    private LocalDate dataStart;
    private Double weight;
    private Double height;
    private Integer age;
    private Double tronco;
    private Double quadril;
    @Column(name = "braco_esquerda")
    private Double bracoEsquerdo;
    @Column(name = "braco_direito")
    private Double bracoDireito;
    @Column(name = "perna_esquerdo")
    private Double pernaEsquerda;
    @Column(name = "perna_direita")
    private Double pernaDireita;
    @Column(name = "panturrilha_esquerda")
    private Double panturrilhaEsquerda;
    @Column(name = "panturrilha_direita")
    private Double panturrilhaDireita;

    public UserMetrics() {}

    public UserMetrics(UserEntity userEntity) {
        this.user = userEntity;
    }

    public UserMetrics(Long id, UserEntity user, LocalDate dataStart, Double weight,
                       Double height, Integer age, Double tronco, Double quadril,
                       Double bracoEsquerdo,
                       Double bracoDireito, Double pernaEsquerda, Double pernaDireita,
                       Double panturrilhaEsquerda, Double panturrilhaDireita) {

        this.id = id;
        this.user = user;
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
