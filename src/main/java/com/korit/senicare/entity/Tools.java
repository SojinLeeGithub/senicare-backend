package com.korit.senicare.entity;

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
public class Tools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer toolNumber;

    private String name;
    private String purpose;
    private Integer count;

    
}
