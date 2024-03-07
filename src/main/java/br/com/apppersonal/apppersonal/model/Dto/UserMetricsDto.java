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
    
    private Double torso;
    
    private Double hip;
    
    private Double leftArm;
    
    private Double rightArm;
    
    private Double leftLeg;
    
    private Double rightLeg;
    
    private Double leftCalf;
    
    private Double rightCalf;

    public UserMetricsDto(Long id,
                          Long userId, Role role,
                          LocalDate dataStart,
                          Double weight, Double height,
                          Integer age, Double torso,
                          Double hip, Double leftArm,
                          Double rightArm, Double leftLeg,
                          Double rightLeg, Double leftCalf, Double rightCalf) {
        this.id = id;
        this.userId = userId;
        this.role = role;
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
