package com.korit.senicare.entity;

import com.korit.senicare.dto.request.tool.PatchToolRequestDto;
import com.korit.senicare.dto.request.tool.PostToolRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "tools")
@Table(name = "tools")
public class ToolEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer toolNumber;

    private String name;
    private String purpose;
    private Integer count;

    // tooNumber 상태 = null
    public ToolEntity(PostToolRequestDto dto) {
        this.name = dto.getName();
        this.purpose = dto.getPurpose();
        this.count = dto.getCount();
    }

    // tooNumber 상태 = 존재하는 값이 조회됨
    public void patch(PatchToolRequestDto dto) {
        this.name = dto.getName();
        this.purpose = dto.getPurpose();
        this.count = dto.getCount();

    }

    
}
