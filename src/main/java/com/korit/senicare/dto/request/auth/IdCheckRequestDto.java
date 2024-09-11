package com.korit.senicare.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 아이디 중복확인 Request Body DTO
// 캡슐화에 필요한 매서드
@Getter
@Setter
@NoArgsConstructor
public class IdCheckRequestDto {

   @NotBlank
    private String userId;
    
}
