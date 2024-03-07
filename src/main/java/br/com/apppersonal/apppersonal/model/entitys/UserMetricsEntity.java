package br.com.apppersonal.apppersonal.model.entitys;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "user_metrics")
public class UserMetricsEntity extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "data_start")
    private LocalDate dataStart;
    private Double weight;
    private Double height;
    private Integer age;
    private Double torso;
    private Double hip;
    @Column(name = "left_arm")
    private Double leftArm;
    @Column(name = "right_arm")
    private Double rightArm;
    @Column(name = "left_leg")
    private Double leftLeg;
    @Column(name = "right_leg")
    private Double rightLeg;
    @Column(name = "left_calf")
    private Double leftCalf;
    @Column(name = "right_calf")
    private Double rightCalf;

    public UserMetricsEntity() {}

    public UserMetricsEntity(
            UserEntity user, LocalDate dataStart, Double weight,
            Double height, Integer age, Double torso, Double hip,
            Double leftArm,
            Double rightArm, Double leftLeg, Double rightLeg,
            Double leftCalf, Double rightCalf)
    {
        this.user = user;
        this.dataStart = dataStart;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.torso = torso;
        this.hip = hip;
        this.leftArm = leftArm;
        this.rightArm = rightArm;
        this.leftLeg = leftLeg;
        this.rightLeg = rightLeg;
        this.leftCalf = leftCalf;
        this.rightCalf = rightCalf;
    }
}
