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



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CareRecords")
@Table(name = "CareRecords")
public class CareRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer recordNumber;

    private String recordDate;
    private String contents;
    private Integer usedTool;
    private Integer count;
    private String charger;
    private Integer customNumber;
    
}