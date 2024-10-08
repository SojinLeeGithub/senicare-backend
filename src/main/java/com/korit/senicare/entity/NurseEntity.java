package com.korit.senicare.entity;


import com.korit.senicare.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity(name="nurses")
@Table(name="nurses")
public class NurseEntity {

    @Id
    private String userId;

    private String password;
    private String name;
    private String telNumber;
    private String joinPath;
    private String snsId;
    
    // 생성자 직접 만듦
    public NurseEntity(SignUpRequestDto dto) {
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.telNumber = dto.getTelNumber();
        this.joinPath = dto.getJoinPath();
        this.snsId = dto.getSnsId();
    }

    
}
