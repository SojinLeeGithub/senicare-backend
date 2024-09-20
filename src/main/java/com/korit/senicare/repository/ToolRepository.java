package com.korit.senicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.ToolEntity;

@Repository
public interface ToolRepository extends JpaRepository<ToolEntity, Integer> {
    // 하나 또는 0개 반환
    ToolEntity findByToolNumber(Integer toolNumber);

    // 다수 반환 = 그래서 리스트 형태
    List<ToolEntity> findByOrderByToolNumberDesc();

}
